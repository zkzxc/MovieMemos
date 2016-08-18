package com.zk.moviememos.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.zk.moviememos.contract.SeenMemosContract;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.view.fragment.SeenMemosFragment;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SeenMemosPresenter implements SeenMemosContract.Presenter {

    private static MemoModel mMemoModel;
    private SeenMemosContract.View mView;

    private static SeenMemosPresenter mPresenter;

    public static SeenMemosPresenter getInstance(MemoModel memoModel, SeenMemosContract.View view) {
        if (mPresenter == null) {
            mPresenter = new SeenMemosPresenter(memoModel, view);
        }
        return mPresenter;
    }

    private SeenMemosPresenter(MemoModel memoModel, SeenMemosContract.View view) {
        this.mMemoModel = memoModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void init() {
        loadMemos(false);
    }

    @Override
    public void loadMemos(boolean forceUpdate) {
        mView.showProgress();
        mMemoModel.loadMemos(new MemoModel.LoadMemosCallback() {
            @Override
            public void onMemosLoaded(final List<Memo> memos) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mView.hideProgress();
                                if (!((SeenMemosFragment) mView).isAdded()) {
                                    return;
                                } else {
                                    if (memos.isEmpty()) {
                                        mView.showNoMemo();
                                    } else {
                                        // TODO: 2016/7/23 展示已看记录列表

                                    }

                                }

                            }
                        });

                    }
                }).start();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
