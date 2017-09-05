package com.zk.moviememos.model;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.vo.DoubanSearchObject;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

import rx.Subscriber;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MovieModel {

    void getMovies(String keyword, int start, Subscriber<DoubanSearchObject> subscriber);

    void getMovieById(String id, Subscriber<DoubanMovie> subscriber);

    void getAndUpdateMovieById(String id, Subscriber<DoubanMovie> subscriber);

    interface GetMoviesCallBack {

        void onSuccess(List<SimpleDoubanMovie> simpleDoubanMovies);

        void onFailure();
    }

    interface GetMovieCallBack {

        void onSuccess(DoubanMovie doubanMovie);

        void onFailure();
    }

}
