package com.zk.moviememos.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.contract.MemoDetailContract;
import com.zk.moviememos.databinding.FragmentMemoDetailBinding;
import com.zk.moviememos.po.Memo;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MemoDetailFragment extends BaseFragment<MemoDetailContract.Presenter> implements MemoDetailContract.View{

    public static final String TAG = "MemoDetailFragment";

    private FragmentMemoDetailBinding binding;

    public static MemoDetailFragment getInstance() {
        return new MemoDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemoDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void showMemo(Memo memo) {
        binding.setMemo(memo);
        binding.executePendingBindings();
    }
}
