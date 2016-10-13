package com.zk.moviememos.contract;

import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface SeenMemosContract {

    interface View extends BaseView<Presenter> {
        void showNoMemo();
        void showSeenMemos(List<SimpleMovieMemo> memos);
    }

    interface Presenter extends BasePresenter {
        void loadMemos(boolean forceUpdate);
    }
}
