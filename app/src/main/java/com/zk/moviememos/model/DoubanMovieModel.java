package com.zk.moviememos.model;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.zk.moviememos.api.ClientBuilder;
import com.zk.moviememos.api.DoubanMoveApi;
import com.zk.moviememos.constants.DefaultConfigs;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.util.PinyinUtils;
import com.zk.moviememos.vo.DoubanSearchObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class DoubanMovieModel implements MovieModel {

    private MovieMemoSQLiteOpenHelper helper;
    private Retrofit retrofit;
    private DoubanMoveApi doubanMoveApi;

    private DoubanMovieModel() {
        helper = MovieMemoSQLiteOpenHelper.getInstance();
        retrofit = new Retrofit.Builder()
                .client(ClientBuilder.getInstance().getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(DefaultConfigs.DOUBAN_BASE_URL)
                .build();
        doubanMoveApi = retrofit.create(DoubanMoveApi.class);
    }

    public static class SingletonHolder{
        private static final DoubanMovieModel INSTANCE = new DoubanMovieModel();
    }

    public static DoubanMovieModel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void getMovies(String keyword, int start, Subscriber<DoubanSearchObject> subscriber) {
        /*Call<DoubanSearchObject> call = doubanMoveApi.queryMoviesByKeyword(keyword);
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
        });*/
        doubanMoveApi.queryMoviesByKeyword(keyword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getMovieById(String id, Subscriber<DoubanMovie> subscriber) {
        /*Call<DoubanMovie> call = doubanMoveApi.getMovieById(id);
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
        });*/
        doubanMoveApi.getMovieById(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getAndUpdateMovieById(String id, Subscriber<DoubanMovie> subscriber) {
        doubanMoveApi.getMovieById(id)
                .doOnNext(new Action1<DoubanMovie>() {
                    @Override
                    public void call(DoubanMovie doubanMovie) {
                        updateMovie(doubanMovie);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void updateMovie(DoubanMovie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (movie.getTitle() != null) {
                values.put("title", movie.getTitle());
                values.put("pinyin_title", PinyinUtils.hanziToPinyin(movie.getTitle()));
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
