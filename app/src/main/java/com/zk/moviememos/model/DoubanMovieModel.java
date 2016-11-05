package com.zk.moviememos.model;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.constants.DefaultConfigs;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.DoubanSearchObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DoubanMovieModel implements MovieModel {

    private static DoubanMovieModel mModel;
    private static MovieMemoSQLiteOpenHelper helper;

    private DoubanMovieModel() {
    }

    public static DoubanMovieModel getInstance() {
        helper = MovieMemoSQLiteOpenHelper.getInstance(App.getContext());
        if (mModel == null) {
            mModel = new DoubanMovieModel();
        }
        return mModel;
    }

    Retrofit retrofit = new Retrofit.Builder().baseUrl(DefaultConfigs.DOUBAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    IDoubanMovieBiz doubanMovieBiz = retrofit.create(IDoubanMovieBiz.class);

    private interface IDoubanMovieBiz {

        @GET("search")
        Call<DoubanSearchObject> getMoviesByKeyword(@Query("q") String keyword);

        @GET("subject/{id}")
        Call<DoubanMovie> getMovieById(@Path("id") String id);
    }

    @Override
    public void getMovies(String keyword, int start, final GetMoviesCallBack callBack) {
        Call<DoubanSearchObject> call = doubanMovieBiz.getMoviesByKeyword(keyword);
        call.enqueue(new Callback<DoubanSearchObject>() {
            @Override
            public void onResponse(Call<DoubanSearchObject> call, Response<DoubanSearchObject> response) {
                LogUtils.i(this, "response::::::" + response.body().getTitle());
                callBack.onSuccess(response.body().getSubjects());
            }

            @Override
            public void onFailure(Call<DoubanSearchObject> call, Throwable t) {
                LogUtils.i(this, "trowable::::::" + t.getMessage() + "!!!!!!");
                t.printStackTrace();
                callBack.onFailure();
            }
        });
    }

    @Override
    public void getMovieById(String id, final GetMovieCallBack callBack) {
        Call<DoubanMovie> call = doubanMovieBiz.getMovieById(id);
        call.enqueue(new Callback<DoubanMovie>() {
            @Override
            public void onResponse(Call<DoubanMovie> call, Response<DoubanMovie> response) {
                if (response.code() != 200) {
                    LogUtils.i(this, "code::::::" + response.code() + "     message::::::" + response.message());
                    Toast.makeText(App.getContext(), R.string.movie_item_not_available, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    LogUtils.i(this, "response::::::" + response.body().getTitle());
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<DoubanMovie> call, Throwable t) {
                LogUtils.i(this, "trowable::::::" + t.getMessage() + "!!!!!!");
                t.printStackTrace();
                callBack.onFailure();
            }
        });
    }

    @Override
    public void updateMovie(DoubanMovie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (movie.getTitle() != null) {
                values.put("title", movie.getTitle());
            }
            if (movie.getOriginal_title() != null) {
                values.put("original_title", movie.getOriginal_title());
            }
            if (movie.getAka() != null) {
                values.put("aka", movie.getAka());
            }
            if (movie.getRating() != null) {
                values.put("douban_score", movie.getRating().getAverage());
            }
            if (movie.getImages().getLarge() != null) {
                values.put("image_large", movie.getImages().getLarge());
            }
            if (movie.getImages().getMedium() != null) {
                values.put("image_medium", movie.getImages().getMedium());
            }
            if (movie.getImages().getSmall() != null) {
                values.put("image_small", movie.getImages().getSmall());
            }
            if (movie.getSubtype() != null) {
                values.put("subtype", movie.getSubtype());
            }
            if (movie.getDirectorsNames() != null) {
                values.put("directors", movie.getDirectorsNames());
            }
            if (movie.getCastsNames() != null) {
                values.put("casts", movie.getCastsNames());
            }
            if (movie.getYear() != null) {
                values.put("year", movie.getYear());
            }
            if (movie.getGenres() != null) {
                values.put("genres", movie.getGenres());
            }
            if (movie.getCountries() != null) {
                values.put("countries", movie.getCountries());
            }
            if (movie.getSummary() != null) {
                values.put("summary", movie.getSummary());
            }
            if (movie.getSeasons_count() != null) {
                values.put("seasons_count", movie.getSeasons_count());
            }
            if (movie.getEpisodes_count() != null) {
                values.put("episodes_count", movie.getEpisodes_count());
            }
            db.update("movie", values, "id = ?", new String[]{movie.getId()});
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
