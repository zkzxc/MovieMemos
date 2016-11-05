package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.WantMemosContract;
import com.zk.moviememos.model.MemoModel;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class WantMemosPresenter implements WantMemosContract.Presenter {

    private static MemoModel mMemoModel;
    private WantMemosContract.View mView;

    private static WantMemosPresenter mPresenter;

    public static WantMemosPresenter getInstance(MemoModel memoModel, WantMemosContract.View view) {
        if (mPresenter == null) {
            mPresenter = new WantMemosPresenter(memoModel, view);
        }
        return mPresenter;
    }

    private WantMemosPresenter(MemoModel memoModel, WantMemosContract.View view) {
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
