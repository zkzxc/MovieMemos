package com.zk.moviememos.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.contract.SeenMemosContract;
import com.zk.moviememos.databinding.SimpleMovieMemoItemBinding;
import com.zk.moviememos.util.LogUtils;
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

    private View mRoot;
    private View mNoMemoView;
    //private RecyclerView mRecycleView;
    PinnedSectionListView listView;
    private SimpleMovieMemoListViewAdapter mAdapter;
    private int page;
    private boolean loadedAll;
    private View footer;

    public static SeenMemosFragment getInstance() {
        return new SeenMemosFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_seen, container, false);
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

        listView = (PinnedSectionListView) mRoot.findViewById(R.id.lv_memoList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        mAdapter = new SimpleMovieMemoListViewAdapter(mActivity, new ArrayList<SimpleMovieMemo>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    bundle.putString("action", BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MEMO);
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
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int lastVisiblePosition = -1;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtils.d(this, "firstVisibleItem:    " + firstVisibleItem + "    visibleItemCount:    " +
                        visibleItemCount + "    totalItemCount:    " + totalItemCount);
                LogUtils.d(this, "getFirstVisiblePosition:    " + listView.getFirstVisiblePosition() +
                        "    getLastVisiblePosition:    " + listView.getLastVisiblePosition());
                if (lastVisiblePosition != listView.getLastVisiblePosition()) {
                    lastVisiblePosition = listView.getLastVisiblePosition();
                    if (lastVisiblePosition == totalItemCount - 1 && !loadedAll) {
                        page++;
                        mPresenter.loadMemos(BusinessConstans.PAGE_SIZE_SEEN_MEMOS * page);
                    }
                }
            }
        });

        mNoMemoView = mRoot.findViewById(R.id.cv_welcome);
        return mRoot;
    }

    @Override
    public void showNoMemo() {
        if (isActive() && listView != null && mNoMemoView != null) {
            listView.setVisibility(View.GONE);
            mNoMemoView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSeenMemos(List<SimpleMovieMemo> memos) {
        LogUtils.d(this, "page: ............................  " + page);
        if (page == 0) {
            mAdapter.setList(memos);
        } else {
            mAdapter.addToList(memos);
        }
    }

    @Override
    public void hideNoMemo() {
        if (isActive() && listView != null && mNoMemoView != null) {
            listView.setVisibility(View.VISIBLE);
            mNoMemoView.setVisibility(View.GONE);
        }
    }

    @Override
    public void addFooter() {
        loadedAll = true;
        page--;
        if (listView.getFooterViewsCount() == 0) {
            footer = View.inflate(mActivity, R.layout.footer_have_loaded_all, null);
            listView.addFooterView(footer);
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

    @Override
    public void clear() {
        page = 0;
        loadedAll = false;
        mAdapter.setList(null);
        listView.removeFooterView(footer);
        listView.scrollTo(0, 0);
    }

}
