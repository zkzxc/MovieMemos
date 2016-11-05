package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.TopContract;
import com.zk.moviememos.model.MemoModel;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class TopPresenter implements TopContract.Presenter {

    private static MemoModel mMemoModel;
    private TopContract.View mView;

    private static TopPresenter mPresenter;

    public static TopPresenter getInstance(MemoModel memoModel, TopContract.View view) {
        if (mPresenter == null) {
            mPresenter = new TopPresenter(memoModel, view);
        }
        return mPresenter;
    }

    private TopPresenter(MemoModel memoModel, TopContract.View view) {
        this.mMemoModel = memoModel;
        this.mView = view;
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

}
