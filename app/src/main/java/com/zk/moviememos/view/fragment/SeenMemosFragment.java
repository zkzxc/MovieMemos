package com.zk.moviememos.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.moviememos.R;
import com.zk.moviememos.contract.SeenMemosContract;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.presenter.SearchMoviesPresenter;
import com.zk.moviememos.util.FragmentUtils;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SeenMemosFragment extends BaseFragment<SeenMemosContract.Presenter> implements SeenMemosContract.View {

    private static SeenMemosFragment mFragment;

    private View mNoMemoView;
    private FloatingActionButton mFab;
    private RecyclerView mRecycleView;

    public SeenMemosFragment(){
        this.mFragment = this;
    }

    public static SeenMemosFragment getInstance() {
        if (mFragment == null) {
            mFragment = new SeenMemosFragment();
        }
        return mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_seen, container, false);

        mRecycleView = (RecyclerView) root.findViewById(R.id.rv_memoList);

        mNoMemoView = root.findViewById(R.id.ll_noMemo);

        mFab = (FloatingActionButton) root.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMoviesFragment searchMovieFragment = SearchMoviesFragment.getInstance(true);
                SearchMoviesPresenter searchMoviePresenter = SearchMoviesPresenter.getInstance(
                        DoubanMovieModel.getInstance(), searchMovieFragment);
                FragmentUtils.switchFragment(SeenMemosFragment.this.getFragmentManager(),
                        SeenMemosFragment.this, searchMovieFragment, true);
            }
        });
        return root;
    }


    @Override
    public void showNoMemo() {
        if (isActive() && mRecycleView != null && mNoMemoView != null) {
            mRecycleView.setVisibility(View.GONE);
            mNoMemoView.setVisibility(View.VISIBLE);
        }
    }

    public void hideNoMemo() {
        if (isActive() && mRecycleView != null && mNoMemoView != null) {
            mRecycleView.setVisibility(View.VISIBLE);
            mNoMemoView.setVisibility(View.GONE);
        }
    }

}
