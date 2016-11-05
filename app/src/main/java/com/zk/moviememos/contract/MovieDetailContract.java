package com.zk.moviememos.contract;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showMovieDetail(DoubanMovie doubanMovie);

        void showMemos(List<SimpleMovieMemo> memos);
    }

    interface Presenter extends BasePresenter {

        void getMemosByMovieId(String movieId);
    }
}
