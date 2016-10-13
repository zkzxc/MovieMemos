package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.LocalMemoModel;
import com.zk.moviememos.model.MovieModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.view.Adapter.SimpleDoubanMovieAdapter;
import com.zk.moviememos.view.fragment.SeenMemosFragment;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private DoubanMovieModel movieModel;
    private LocalMemoModel memoModel;
    private MovieContract.View mView;
    private String movieId;

    public static MoviePresenter getInstance(DoubanMovieModel movieModel, LocalMemoModel memoModel,
                                             MovieContract.View view, String movieId) {
        MoviePresenter presenter = new MoviePresenter(movieModel, memoModel, view, movieId);
        return presenter;
    }


    private MoviePresenter(DoubanMovieModel movieModel, LocalMemoModel memoModel,
                           MovieContract.View view, String movieId) {
        this.movieModel = movieModel;
        this.memoModel = memoModel;
        this.mView = view;
        this.movieId = movieId;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
    }

    public void getMovie(String todo) {
        if (SimpleDoubanMovieAdapter.SHOW_MOVIE_DETAIL.equals(todo)) {
            mView.showProgress();
            movieModel.getMovieById(movieId, new MovieModel.GetMovieCallBack() {
                @Override
                public void onSuccess(DoubanMovie doubanMovie) {
                    if (mView.isActive()) {
                        mView.hideProgress();
                        mView.showMovie(doubanMovie);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        } else if (SeenMemosFragment.SHOW_MEMO.equals(todo)) {

        }

    }
}
