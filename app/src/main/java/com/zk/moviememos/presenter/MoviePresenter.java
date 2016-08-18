package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.po.DoubanMovie;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private DoubanMovieModel mModel;
    private MovieContract.View mView;
    private String movieId;

    private static MoviePresenter mPresenter;

    public static MoviePresenter getInstance(DoubanMovieModel model, MovieContract.View view, String movieId) {
        if (mPresenter == null) {
            mPresenter = new MoviePresenter(model, view, movieId);
        }
        return mPresenter;
    }


    private MoviePresenter(DoubanMovieModel model, MovieContract.View view, String movieId) {
        this.mModel = model;
        this.mView = view;
        this.movieId = movieId;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
        getMovie();
    }

    public void getMovie() {
        mView.showProgress();
        mModel.getMovieById(movieId, new MovieModel.GetMovieCallBack() {
            @Override
            public void onSuccess(DoubanMovie doubanMovie) {
                mView.hideProgress();
                mView.showMovie(doubanMovie);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
