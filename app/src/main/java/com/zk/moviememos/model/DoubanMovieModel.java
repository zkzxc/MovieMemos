package com.zk.moviememos.model;

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

    private DoubanMovieModel(){}

    public static DoubanMovieModel getInstance() {
        if (mModel == null) {
            mModel = new DoubanMovieModel();
        }
        return mModel;
    }

    Retrofit retrofit = new Retrofit.Builder().baseUrl(DefaultConfigs.DOUBAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    IDoubanMovieBiz doubanMovieBiz = retrofit.create(IDoubanMovieBiz.class);

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
                LogUtils.i(this, "response::::::" + response.body().getTitle());
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DoubanMovie> call, Throwable t) {
                LogUtils.i(this, "trowable::::::" + t.getMessage() + "!!!!!!");
                t.printStackTrace();
                callBack.onFailure();
            }
        });
    }


    private interface IDoubanMovieBiz {

        @GET("search")
        Call<DoubanSearchObject> getMoviesByKeyword(@Query("q") String keyword);

        @GET("subject/{id}")
        Call<DoubanMovie> getMovieById(@Path("id") String id);
    }
}
