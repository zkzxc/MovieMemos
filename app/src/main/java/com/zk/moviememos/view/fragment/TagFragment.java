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
public class TagFragment extends BaseFragment<TagContract.Presenter> {

    private static TagFragment mFragment;

    public TagFragment(){
        this.mFragment = this;
    }

    public static TagFragment getInstance() {
        if (mFragment == null) {
            mFragment = new TagFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag, container, false);
        return root;
    }
}
