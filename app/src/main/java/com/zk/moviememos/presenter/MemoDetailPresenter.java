package com.zk.moviememos.presenter;

import com.zk.moviememos.contract.MemoDetailContract;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.view.activity.MovieActivity;
import com.zk.moviememos.view.fragment.MemoDetailFragment;

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
    public void initOnCreate() {

    }

    @Override
    public void loadOnResume() {
        getMemoById();
    }

    @Override
    public void loadOnHiddenChanged(boolean hidden) {
        if (!hidden) {
            getMemoById();
        }
    }

    public void getMemoById() {
        mModel.findMemoByMemoId(memoId, new MemoModel.LoadMemoCallback() {
            @Override
            public void onMemoLoaded(final Memo memo) {
                MemoDetailPresenter.this.memo = memo;
                if (mView.isActive()) {
                    mView.showMemo(memo);
                    ((MovieActivity) ((MemoDetailFragment) mView).getActivity()).setOldMemo(memo);
                }
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

}
