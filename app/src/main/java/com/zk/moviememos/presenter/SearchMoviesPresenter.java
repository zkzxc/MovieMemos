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

    public static SearchMoviesPresenter getInstance(DoubanMovieModel model, SearchMoviesContract.View view) {
        return new SearchMoviesPresenter(model, view);
    }

    private SearchMoviesPresenter(DoubanMovieModel model, SearchMoviesContract.View view) {
        this.mModel = model;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void initOnCreate() {

    }

    @Override
    public void loadOnResume() {

    }

    @Override
    public void loadOnHiddenChanged(boolean hidden) {

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
    public void onItemclick(String action, String movieId, String title, boolean isTv, String posterUrl, ImageView
            imageView, String transitionName) {
        mView.showItem(action, movieId, title, isTv, posterUrl, imageView, transitionName);
    }
}
