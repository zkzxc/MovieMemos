package com.zk.moviememos.contract;

import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface WantMemosContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
