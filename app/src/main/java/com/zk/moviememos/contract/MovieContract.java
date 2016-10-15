package com.zk.moviememos.contract;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MovieContract {

    interface View extends BaseView<Presenter> {

        void showMovie(DoubanMovie doubanMovie);

        void showMovieFromMemo(Memo memo);
    }

    interface Presenter extends BasePresenter {

    }
}
