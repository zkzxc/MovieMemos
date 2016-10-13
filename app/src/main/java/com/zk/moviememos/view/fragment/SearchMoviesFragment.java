package com.zk.moviememos.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.contract.SearchMoviesContract;
import com.zk.moviememos.databinding.FragmentSearchBinding;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.view.Adapter.SimpleDoubanMovieAdapter;
import com.zk.moviememos.view.activity.MainActivity;
import com.zk.moviememos.view.activity.MovieActivity;
import com.zk.moviememos.vo.SimpleDoubanMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SearchMoviesFragment extends BaseFragment<SearchMoviesContract.Presenter> implements
        SearchMoviesContract.View {

    public static final String TAG = "SearchMovieFragment";

    public static final String ARGUMENT = "argument";

    private static SearchMoviesFragment mFragment;

    private boolean needSearchViewExpand;
    private SearchView mSearchView;
    private RecyclerView rvResults;
    private LinearLayout llBeforeSearch;
    private LinearLayout llNoResult;
    private SimpleDoubanMovieAdapter mAdapter;

    public SearchMoviesFragment() {
        this.mFragment = this;
    }

    public static SearchMoviesFragment getInstance(boolean needSearchViewExpand) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARGUMENT, needSearchViewExpand);
        SearchMoviesFragment searchMoviesFragment = null;
        if (mFragment == null) {
            mFragment = new SearchMoviesFragment();
        }
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            needSearchViewExpand = bundle.getBoolean(ARGUMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        rvResults = fragmentSearchBinding.rvResults;
        llBeforeSearch = fragmentSearchBinding.llBeforeSearch;
        llNoResult = fragmentSearchBinding.llNoResult;

        rvResults.setHasFixedSize(true);
        rvResults.setLayoutManager(new LinearLayoutManager(App.getContext()));
        rvResults.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SimpleDoubanMovieAdapter(mActivity, new ArrayList<SimpleDoubanMovie>(0));
        mAdapter.setOnItemClickListener((SimpleDoubanMovieAdapter.OnItemClickListener) mPresenter);
        rvResults.setAdapter(mAdapter);
        View root = fragmentSearchBinding.getRoot();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setQueryHint(ResourseUtils.getString(R.string.search_hint));
        if (needSearchViewExpand) {
            mSearchView.onActionViewExpanded();
            needSearchViewExpand = false;
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return mPresenter.onQueryTextSubmit(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return mPresenter.onQueryTextChange(newText);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showResults(List<SimpleDoubanMovie> movies) {
        if (isActive()) {
            rvResults.setVisibility(View.VISIBLE);
            mAdapter.setList(movies);
        }
    }

    @Override
    public void hideResults() {
        if (isActive() && rvResults.getVisibility() == View.VISIBLE) {
            rvResults.setVisibility(View.GONE);
        }
    }

    @Override
    public void showItem(String todo, String movieId, String title, boolean isTv, String posterUrl, ImageView
            imageView, String transitionName) {
        Bundle bundle = new Bundle();
        bundle.putString("movieId", movieId);
        bundle.putString("title", title);
        bundle.putBoolean("isTv", isTv);
        bundle.putString("posterUrl", posterUrl);
        bundle.putString("todo", todo);
        Intent intent = new Intent(mActivity, MovieActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, imageView,
                    transitionName);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void hideBeforeSearch() {
        if (isActive() && llBeforeSearch.getVisibility() != View.GONE) {
            llBeforeSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoResult() {
        if (isActive() && llNoResult.getVisibility() != View.VISIBLE) {
            llNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideNoResult() {
        if (isActive() && llNoResult.getVisibility() != View.GONE) {
            llNoResult.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideInput() {
        mSearchView.clearFocus();
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
