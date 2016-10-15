package com.zk.moviememos.presenter;

import android.os.Handler;
import android.os.Looper;

import com.zk.moviememos.contract.MemoDetailContract;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.po.Memo;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MemoDetailPresenter implements MemoDetailContract.Presenter {

    private MemoModel mModel;
    private MemoDetailContract.View mView;
    private String memoId;
    private Memo memo;

    public static MemoDetailPresenter getInstance(MemoModel model, MemoDetailContract.View view, String memoId) {
        MemoDetailPresenter presenter = new MemoDetailPresenter(model, view, memoId);
        return presenter;
    }


    private MemoDetailPresenter(MemoModel model, MemoDetailContract.View view, String memoId) {
        this.mModel = model;
        this.mView = view;
        this.memoId = memoId;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
        getMemoById();
    }

    public void getMemoById() {
        mModel.findMemoByMemoId(memoId, new MemoModel.LoadMemoCallback() {
            @Override
            public void onMemoLoaded(final Memo memo) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MemoDetailPresenter.this.memo = memo;
                        if (mView.isActive()) {
                            mView.showMemo(memo);
                        }
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void deleteMemo(String memoId) {
        mModel.deleteMemoById(memoId);
    }

    public Memo getMemo() {
        return this.memo;
    }

}
