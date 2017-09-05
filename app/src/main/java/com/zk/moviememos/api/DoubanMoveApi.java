package com.zk.moviememos.api;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.vo.DoubanSearchObject;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zk <zkzxc1988@163.com>.
 */

public interface DoubanMoveApi {

    @Headers("cache-control: public, max-age=10")
    @GET("search")
    Observable<DoubanSearchObject> queryMoviesByKeyword(@Query("q") String keyword);

    @Headers("cache-control: public, max-age=30")
    @GET("subject/{id}")
    Observable<DoubanMovie> getMovieById(@Path("id") String id);
}
