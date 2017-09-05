package com.zk.moviememos.presenter;

import android.widget.ImageView;
import android.widget.Toast;

import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.contract.SearchMoviesContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.view.Adapter.SimpleDoubanMovieAdapter;
import com.zk.moviememos.vo.DoubanSearchObject;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.List;

import rx.Subscriber;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SearchMoviesPresenter implements SearchMoviesContract.Presenter, SimpleDoubanMovieAdapter
        .OnItemClickListener {

    private DoubanMovieModel mModel;
    private SearchMoviesContract.View mView;
    private Subscriber<DoubanSearchObject> subscriber;

    public static SearchMoviesPresenter getInstance(DoubanMovieModel model, SearchMoviesContract.View view) {
        return new SearchMoviesPresenter(model, view);
    }

    private SearchMoviesPresenter(DoubanMovieModel model, SearchMoviesContract.View view) {
        this.mModel = model;
        this.mView = view;
        this.subscriber = new Subscriber<DoubanSearchObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(App.getContext(), R.string.movie_search_not_available, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(DoubanSearchObject doubanSearchObject) {
                if (mView.isActive()) {
                    mView.hideProgress();
                    mView.hideInput();
                    List<SimpleDoubanMovie> movies = doubanSearchObject.getSubjects();
                    if (movies.size() > 0) {
                        mView.showResults(movies);
                    } else {
                        mView.showNoResult();
                    }
                }
            }
        };

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

    @Override
    public void searchMovies(String query, int start) {
        mView.hideBeforeSearch();
        mView.hideNoResult();
        mView.hideResults();
        mView.showProgress();
        mModel.getMovies(query, start, subscriber);
    }

    @Override
    public void onItemclick(String action, String movieId, String title, boolean isTv, String posterUrl, ImageView
            imageView, String transitionName) {
        mView.showItem(action, movieId, title, isTv, posterUrl, imageView, transitionName);
    }
}
