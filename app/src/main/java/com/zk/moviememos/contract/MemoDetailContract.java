package com.zk.moviememos.contract;

import com.zk.moviememos.po.Memo;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;

/**
 * Created by zk <zkzxc1988@163.com>.
 */

public interface MemoDetailContract {

    interface View extends BaseView<MemoDetailContract.Presenter> {

        void showMemo(Memo memo);
    }

    interface Presenter extends BasePresenter {

        void deleteMemo(String memoId);
    }
}
