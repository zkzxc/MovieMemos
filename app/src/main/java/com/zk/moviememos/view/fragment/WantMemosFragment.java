package com.zk.moviememos.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.R;
import com.zk.moviememos.contract.WantMemosContract;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class WantMemosFragment extends BaseFragment<WantMemosContract.Presenter> {

    private static WantMemosFragment mFragment;

    public static WantMemosFragment getInstance() {
        if (mFragment == null) {
            mFragment = new WantMemosFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_want, container, false);
        return root;
    }
}
