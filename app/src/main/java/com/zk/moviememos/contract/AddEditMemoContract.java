package com.zk.moviememos.contract;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface AddEditMemoContract {

    interface View extends BaseView<Presenter> {

        void showMemo(Memo memo);
    }

    interface Presenter extends BasePresenter {

        void saveMemo(DoubanMovie movie, Memo memo);

        void getMemo(String memoId);
    }
}
