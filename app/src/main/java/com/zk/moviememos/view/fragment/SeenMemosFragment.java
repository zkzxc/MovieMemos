package com.zk.moviememos.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zk.moviememos.R;
import com.zk.moviememos.contract.SeenMemosContract;
import com.zk.moviememos.databinding.SimpleMovieMemoItemBinding;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.view.Adapter.SimpleMovieMemoListViewAdapter;
import com.zk.moviememos.view.activity.MainActivity;
import com.zk.moviememos.view.activity.MovieActivity;
import com.zk.moviememos.vo.SimpleMovieMemo;
import com.zk.moviememos.widget.PinnedSectionListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SeenMemosFragment extends BaseFragment<SeenMemosContract.Presenter> implements SeenMemosContract.View {

    public static final String TAG = "SeenMemosFragment";
    public static final String SHOW_MEMO = "show_memo";

    private static SeenMemosFragment mFragment;

    private View mNoMemoView;
    private RecyclerView mRecycleView;
    private SimpleMovieMemoListViewAdapter mAdapter;

    public SeenMemosFragment() {
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

//        mRecycleView = (RecyclerView) root.findViewById(R.id.rv_memoList);
//        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mAdapter = new SimpleMovieMemoAdapter(mActivity, new ArrayList<SimpleMovieMemo>());
//        mRecycleView.setAdapter(mAdapter);
//        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mAdapter);
//        mRecycleView.addItemDecoration(decoration);
//        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                decoration.invalidateHeaders();
//            }
//        });

        PinnedSectionListView lv = (PinnedSectionListView) root.findViewById(R.id.lv_memoList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lv.setNestedScrollingEnabled(true);
        }
        mAdapter = new SimpleMovieMemoListViewAdapter(mActivity, new ArrayList<SimpleMovieMemo>());
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleMovieMemoItemBinding binding = DataBindingUtil.findBinding(view);
                if (binding != null) {
                    SimpleMovieMemo memo = binding.getSimpleMovieMemo();
                    Bundle bundle = new Bundle();
                    bundle.putString("memoId", memo.getMemoId());
                    bundle.putString("movieId", memo.getMovieId());
                    bundle.putString("title", memo.getTitle());
                    bundle.putBoolean("isTv", memo.isTv());
                    bundle.putString("posterUrl", memo.getImages().getLarge());
                    bundle.putString("todo", SHOW_MEMO);
                    Intent intent = new Intent(mActivity, MovieActivity.class);
                    intent.putExtras(bundle);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                                binding.ivMovieImageLarge, ResourseUtils.getString(R.string.transition_name_poster));
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            }
        });

        mNoMemoView = root.findViewById(R.id.ll_noMemo);
        return root;
    }

    @Override
    public void showNoMemo() {
        if (isActive() && mRecycleView != null && mNoMemoView != null) {
            mRecycleView.setVisibility(View.GONE);
            mNoMemoView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSeenMemos(List<SimpleMovieMemo> memos) {
        if (memos != null && memos.size() > 0) {
            mAdapter.setList(memos);
        }
    }

    public void hideNoMemo() {
        if (isActive() && mRecycleView != null && mNoMemoView != null) {
            mRecycleView.setVisibility(View.VISIBLE);
            mNoMemoView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {
        if (isActive() && mActivity != null) {
            ((MainActivity) mActivity).showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (isActive() && mActivity != null) {
            ((MainActivity) mActivity).hideProgress();
        }
    }
}
