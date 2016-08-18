package com.zk.moviememos.contract;

import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface SeenMemosContract {

    interface View extends BaseView<Presenter> {
        void showNoMemo();
    }

    interface Presenter extends BasePresenter {
        void loadMemos(boolean forceUpdate);
    }
}
