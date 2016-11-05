package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.TagContract;
import com.zk.moviememos.model.MemoModel;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class TagPresenter implements TagContract.Presenter {

    private static MemoModel mMemoModel;
    private TagContract.View mView;

    private static TagPresenter mPresenter;

    public static TagPresenter getInstance(MemoModel memoModel, TagContract.View view) {
        if (mPresenter == null) {
            mPresenter = new TagPresenter(memoModel, view);
        }
        return mPresenter;
    }

    private TagPresenter(MemoModel memoModel, TagContract.View view) {
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
