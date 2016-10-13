package com.zk.moviememos.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zk.moviememos.R;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.LocalMemoModel;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.presenter.SearchMoviesPresenter;
import com.zk.moviememos.presenter.SeenMemosPresenter;
import com.zk.moviememos.util.FragmentUtils;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.view.fragment.SearchMoviesFragment;
import com.zk.moviememos.view.fragment.SeenMemosFragment;
import com.zk.moviememos.view.fragment.TagFragment;
import com.zk.moviememos.view.fragment.TopFragment;
import com.zk.moviememos.view.fragment.WantMemosFragment;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    private FrameLayout flProgress;
    private ImageView ivProgress;
    private ObjectAnimator mProgressAnimator;

    private FragmentManager mFragmentManager;

    private Fragment mFragment; //记录当前显示的Fragment
    private SeenMemosFragment mSeenMemosFragment;
    private WantMemosFragment mWantMemosFragment;
    private TagFragment mTagFragment;
    private TopFragment mTopFragment;
    private SearchMoviesFragment mSearchMoviesFragment;

    private BasePresenter mPresenter;   //记录当前的Presenter
    private SeenMemosPresenter mSeenMemosPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.drawer_menu_item_title_seen);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, mDrawer,
                mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        flProgress = (FrameLayout) findViewById(R.id.fl_progress);
        ivProgress = (ImageView) findViewById(R.id.iv_progress);
        mProgressAnimator = ObjectAnimator.ofFloat(ivProgress, "rotation", 0f, 360f);
        mProgressAnimator.setDuration(1000);
        mProgressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mProgressAnimator.setInterpolator(new LinearInterpolator());

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchMoviesFragment = (SearchMoviesFragment) mFragmentManager.findFragmentByTag(
                        SearchMoviesFragment.TAG);
                if (mSearchMoviesFragment == null) {
                    mSearchMoviesFragment = SearchMoviesFragment.getInstance(true);
                    SearchMoviesPresenter searchMoviesPresenter = SearchMoviesPresenter.getInstance(
                            DoubanMovieModel.getInstance(), mSearchMoviesFragment);
                }
                FragmentUtils.switchFragment(mFragmentManager, mFragment, mSearchMoviesFragment,
                        mFragment.getClass().getSimpleName(), true);
                mFragment = mSearchMoviesFragment;

                fab.setVisibility(View.GONE);
                AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
                lp.setScrollFlags(0);
                mToolbar.setLayoutParams(lp);
            }
        });

        //初始化Fragment
        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            mSeenMemosFragment = (SeenMemosFragment) mFragmentManager.findFragmentById(R.id.fl_content);
            if (mSeenMemosFragment == null) {
                mSeenMemosFragment = SeenMemosFragment.getInstance();
                mSeenMemosPresenter = SeenMemosPresenter.getInstance(LocalMemoModel.getInstance(this),
                        mSeenMemosFragment);
                FragmentUtils.addfragment(getSupportFragmentManager(), mSeenMemosFragment,
                        R.id.fl_content, SeenMemosFragment.TAG);
                mFragment = mSeenMemosFragment;
                mPresenter = mSeenMemosPresenter;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.d(this, "restore...................");
        mFragmentManager = getSupportFragmentManager();
        mSeenMemosFragment = (SeenMemosFragment) mFragmentManager.findFragmentById(R.id.fl_content);
        mSeenMemosPresenter = SeenMemosPresenter.getInstance(LocalMemoModel.getInstance(this),
                mSeenMemosFragment);
        FragmentUtils.addfragment(getSupportFragmentManager(), mSeenMemosFragment,
                R.id.fl_content);
        mFragment = mSeenMemosFragment;
        mPresenter = mSeenMemosPresenter;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mToolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.nav_item_seen:
                if (mSeenMemosFragment == null) {
                    mSeenMemosFragment = SeenMemosFragment.getInstance();
                }
                switchFragment(mFragment, mSeenMemosFragment);
                break;
            case R.id.nav_item_want:
                if (mWantMemosFragment == null) {
                    mWantMemosFragment = WantMemosFragment.getInstance();
                }
                switchFragment(mFragment, mWantMemosFragment);
                break;
            case R.id.nav_item_tag:
                if (mTagFragment == null) {
                    mTagFragment = TagFragment.getInstance();
                }
                switchFragment(mFragment, mTagFragment);
                break;
            case R.id.nav_item_top:
                if (mTopFragment == null) {
                    mTopFragment = TopFragment.getInstance();
                }
                switchFragment(mFragment, mTopFragment);
                break;
            case R.id.nav_item_settings:
                // TODO: 2016/7/15  打开settingsActivity
                break;
            case R.id.nav_item_nightmode:
                // TODO: 2016/7/15  切换夜间模式
                break;
        }
        mDrawer.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mFragment == mSearchMoviesFragment) {
            AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
            switch (mFragment.getTag()) {
                case SeenMemosFragment.TAG:
                    fab.setVisibility(View.VISIBLE);
                    lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                            AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    mFragment = mSeenMemosFragment;
                    break;
                case WantMemosFragment.TAG:
                    fab.setVisibility(View.VISIBLE);
                    lp.setScrollFlags(0);
                    mFragment = mWantMemosFragment;
                    break;
                case TagFragment.TAG:
                    fab.setVisibility(View.VISIBLE);
                    lp.setScrollFlags(0);
                    mFragment = mTagFragment;
                    break;
                case TopFragment.TAG:
                    fab.setVisibility(View.GONE);
                    lp.setScrollFlags(0);
                    mFragment = mTopFragment;
                    break;
            }
            mToolbar.setLayoutParams(lp);
            super.onBackPressed();
        }
    }

    //点击侧滑菜单切换Fragment
    private void switchFragment(Fragment from, Fragment to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentUtils.switchFragment(mFragmentManager, from, to, false);
        }
    }

    public void showProgress() {
        if (!flProgress.isShown()) {
            flProgress.setVisibility(View.VISIBLE);
        }
        mProgressAnimator.start();
    }

    public void hideProgress() {
        if (mProgressAnimator.isRunning()) {
            mProgressAnimator.end();
        }
        if (flProgress.isShown()) {
            flProgress.setVisibility(View.GONE);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public FloatingActionButton getFab() {
        return fab;
    }
}
