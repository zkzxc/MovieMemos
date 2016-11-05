package com.zk.moviememos.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.R;
import com.zk.moviememos.contract.TopContract;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class TopFragment extends BaseFragment<TopContract.Presenter> implements TopContract.View {

    public static final String TAG = "TopFragment";

    public static TopFragment getInstance() {
        return new TopFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top, container, false);
        return root;
    }
}
