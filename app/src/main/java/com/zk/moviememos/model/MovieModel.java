package com.zk.moviememos.model;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MovieModel {

    void getMovies(String keyword, int start, GetMoviesCallBack callBack);

    void getMovieById(String id, GetMovieCallBack callBack);


    interface GetMoviesCallBack {

        void onSuccess(List<SimpleDoubanMovie> simpleDoubanMovies);

        void onFailure();
    }

    interface GetMovieCallBack {

        void onSuccess(DoubanMovie doubanMovie);

        void onFailure();
    }

}
