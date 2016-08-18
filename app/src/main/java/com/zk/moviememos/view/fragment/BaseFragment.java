package com.zk.moviememos.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.view.BaseView;
import com.zk.moviememos.view.activity.MainActivity;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class BaseFragment<T> extends Fragment implements BaseView<T> {

    protected static final String STATE_IS_HIDDEN = "state_is_hidden";

    protected T mPresenter;
    protected MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if (isHidden) {
                fragmentTransaction.hide(this);
            } else {
                fragmentTransaction.show(this);
            }
            fragmentTransaction.commit();
        }
        ((BasePresenter)mPresenter).init();
    }

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        if (isActive() && mActivity != null) {
            mActivity.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (isActive() && mActivity != null) {
            mActivity.hideProgress();
        }
    }

    /**
     * Fragment是否可用
     * @return true为可用，false为没有添加进Activity或正在被移除
     */
    @Override
    public boolean isActive() {
        return isAdded() && !isRemoving();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }
}
