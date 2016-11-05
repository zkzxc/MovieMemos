package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.AddEditMemoContract;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class AddEditMemoPresenter implements AddEditMemoContract.Presenter {

    private MemoModel mModel;
    private AddEditMemoContract.View mView;
    private String memoId;
    private Memo memo;

    public static AddEditMemoPresenter getInstance(MemoModel model, AddEditMemoContract.View view, String movieId) {
        AddEditMemoPresenter presenter = new AddEditMemoPresenter(model, view, movieId);
        return presenter;
    }


    private AddEditMemoPresenter(MemoModel model, AddEditMemoContract.View view, String memoId) {
        this.mModel = model;
        this.mView = view;
        this.memoId = memoId;
        this.memo = new Memo();
        mView.setPresenter(this);
    }


    @Override
    public void saveMemo(DoubanMovie movie, Memo memo) {
        memo.setAddTime(System.currentTimeMillis());
        mModel.saveMemo(movie, memo);
    }

    @Override
    public void getMemo(String memoId) {
        mModel.findMemoByMemoId(memoId, new MemoModel.LoadMemoCallback() {
            @Override
            public void onMemoLoaded(Memo memo) {
                if (mView.isActive()) {
                    mView.showMemo(memo);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
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
