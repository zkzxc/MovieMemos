package com.zk.moviememos.contract;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MovieContract {

    interface View extends BaseView<Presenter> {

        public void showMovie(DoubanMovie doubanMovie);
    }

    interface Presenter extends BasePresenter {

    }
}
