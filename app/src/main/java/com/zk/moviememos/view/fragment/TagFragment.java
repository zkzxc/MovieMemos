package com.zk.moviememos.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.R;
import com.zk.moviememos.contract.TagContract;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class TagFragment extends BaseFragment<TagContract.Presenter> implements TagContract.View{

    public static final String TAG = "TagFragment";

    public static TagFragment getInstance() {
        return new TagFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag, container, false);
        return root;
    }
}
