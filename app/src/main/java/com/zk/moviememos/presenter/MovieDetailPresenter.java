package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MovieDetailContract;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MemoModel mModel;
    private MovieDetailContract.View mView;
    private String movieId;

    public static MovieDetailPresenter getInstance(MemoModel model, MovieDetailContract.View view, String movieId) {
        MovieDetailPresenter presenter = new MovieDetailPresenter(model, view, movieId);
        return presenter;
    }


    private MovieDetailPresenter(MemoModel model, MovieDetailContract.View view, String movieId) {
        this.mModel = model;
        this.mView = view;
        this.movieId = movieId;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
    }

    public void getMemosByMovieId() {
        mModel.findMemosByMovieId(movieId, new MemoModel.LoadMemosCallback() {
            @Override
            public void onMemosLoaded(List<SimpleMovieMemo> memos) {
                if (mView.isActive()) {
                    mView.showMemos(memos);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
