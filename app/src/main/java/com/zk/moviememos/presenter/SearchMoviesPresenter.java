package com.zk.moviememos.presenter;

import android.widget.ImageView;

import com.zk.moviememos.contract.SearchMoviesContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.view.Adapter.SimpleDoubanMovieAdapter;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SearchMoviesPresenter implements SearchMoviesContract.Presenter, SimpleDoubanMovieAdapter
        .OnItemClickListener {

    private DoubanMovieModel mModel;
    private SearchMoviesContract.View mView;

    private static SearchMoviesPresenter mPresenter;

    public static SearchMoviesPresenter getInstance(DoubanMovieModel model, SearchMoviesContract.View view) {
        if (mPresenter == null) {
            mPresenter = new SearchMoviesPresenter(model, view);
        }
        return mPresenter;
    }

    private SearchMoviesPresenter(DoubanMovieModel model, SearchMoviesContract.View view) {
        this.mModel = model;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void init() {

    }

    private void searchMoviesByKeyword(String keyword, int start, MovieModel.GetMoviesCallBack callBack) {
        mModel.getMovies(keyword, start, callBack);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        mView.hideBeforeSearch();
        mView.hideNoResult();
        mView.hideResults();
        mView.showProgress();
        searchMoviesByKeyword(query, 0, new DoubanMovieModel.GetMoviesCallBack() {
            @Override
            public void onSuccess(List<SimpleDoubanMovie> simpleDoubanMovies) {
                if (mView.isActive()) {
                    mView.hideProgress();
                    mView.hideInput();
                    LogUtils.i(this, "search success!!!");
                    if (simpleDoubanMovies.size() > 0) {
                        mView.showResults(simpleDoubanMovies);
                    } else {
                        mView.showNoResult();
                    }
                }
            }

            @Override
            public void onFailure() {
                LogUtils.i(this, "search fail!!!");
                mView.hideProgress();
                mView.showNoResult();
            }
        });
        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onItemclick(String movieId, String title, String posterUrl, ImageView imageView, String transitionName) {
        mView.showItem(movieId, title, posterUrl, imageView, transitionName);
    }
}
