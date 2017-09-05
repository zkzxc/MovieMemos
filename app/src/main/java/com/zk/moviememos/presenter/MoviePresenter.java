package com.zk.moviememos.presenter;

import android.widget.Toast;

import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.po.DoubanMovie;

import rx.Subscriber;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private DoubanMovieModel movieModel;
    private MovieContract.View mView;
    private String movieId;
    private Subscriber<DoubanMovie> subscriber;

    public static MoviePresenter getInstance(DoubanMovieModel movieModel, MovieContract.View view, String movieId) {
        MoviePresenter presenter = new MoviePresenter(movieModel, view, movieId);
        return presenter;
    }


    private MoviePresenter(DoubanMovieModel movieModel, MovieContract.View view, String movieId) {
        this.movieModel = movieModel;
        this.mView = view;
        this.movieId = movieId;
        this.subscriber = new Subscriber<DoubanMovie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(App.getContext(), R.string.movie_item_not_available, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(DoubanMovie doubanMovie) {
                if (mView.isActive()) {
                    mView.hideProgress();
                    mView.showMovie(doubanMovie);
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

    public void getMovie(String action) {
        mView.showProgress();
        // action==show_memo的时候，调用movieModel的getAndUpdateMovieById()方法，从网络获取电影信息时对数据库的该电影信息
        // 进行异步更新
        if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MEMO.equals(action)) {
            movieModel.getAndUpdateMovieById(movieId, subscriber);
        }
        // action==show_detail或add_memo的时候，调用movieModel的getMovieById()方法，直接从网络获取电影信息并显示
        else {
            movieModel.getMovieById(movieId, subscriber);
        }

    }
}
