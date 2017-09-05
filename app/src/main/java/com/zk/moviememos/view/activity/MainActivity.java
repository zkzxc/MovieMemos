package com.zk.moviememos.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;

import com.zk.moviememos.R;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.LocalMemoModel;
import com.zk.moviememos.model.MovieMemoSQLiteOpenHelper;
import com.zk.moviememos.presenter.BasePresenter;
import com.zk.moviememos.presenter.SearchMoviesPresenter;
import com.zk.moviememos.presenter.SeenMemosPresenter;
import com.zk.moviememos.presenter.TagPresenter;
import com.zk.moviememos.presenter.TopPresenter;
import com.zk.moviememos.presenter.WantMemosPresenter;
import com.zk.moviememos.util.FragmentUtils;
import com.zk.moviememos.view.fragment.BaseFragment;
import com.zk.moviememos.view.fragment.SearchMoviesFragment;
import com.zk.moviememos.view.fragment.SeenMemosFragment;
import com.zk.moviememos.view.fragment.TagFragment;
import com.zk.moviememos.view.fragment.TopFragment;
import com.zk.moviememos.view.fragment.WantMemosFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHAREDPREFERENCES_NAME = "movie_memos_sp";
    public static final String SP_KEY_ISFIRSTENTER = "is_first_enter";

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    private boolean isFirstEnter;

    private CoordinatorLayout cl;
    private FrameLayout flProgress;
    private ImageView ivProgress;
    private ObjectAnimator mProgressAnimator;

    private FragmentManager mFragmentManager;

    private BaseFragment mFragment; //记录当前显示的Fragment
    private BaseFragment lastFragment;
    private SeenMemosFragment mSeenMemosFragment;
    private WantMemosFragment mWantMemosFragment;
    private TagFragment mTagFragment;
    private TopFragment mTopFragment;
    private SearchMoviesFragment mSearchMoviesFragment;

    private BasePresenter mPresenter;   //记录当前的Presenter
    private SeenMemosPresenter mSeenMemosPresenter;
    private WantMemosPresenter mWantMemosPresenter;
    private TagPresenter mTagPresenter;
    private TopPresenter mTopPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        cl = (CoordinatorLayout) findViewById(R.id.cl);
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
                        SearchMoviesFragment.TAG, true);
                lastFragment = mFragment;
                mFragment = mSearchMoviesFragment;

                cl.removeView(fab);
                AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
                lp.setScrollFlags(0);
                mToolbar.setLayoutParams(lp);
            }
        });

        isFirstEnter = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE).getBoolean(SP_KEY_ISFIRSTENTER,
                true);
        if (isFirstEnter) {
            SharedPreferences sp = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
            sp.edit().putBoolean(SP_KEY_ISFIRSTENTER, false).commit();
            MovieMemoSQLiteOpenHelper.getInstance().getWritableDatabase().close();
        }
        flProgress = (FrameLayout) findViewById(R.id.fl_progress);
        ivProgress = (ImageView) findViewById(R.id.iv_progress);
        mProgressAnimator = ObjectAnimator.ofFloat(ivProgress, "rotation", 0f, 360f);
        mProgressAnimator.setDuration(1000);
        mProgressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mProgressAnimator.setInterpolator(new LinearInterpolator());

        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            showSeenMemosFragment();
        }
    }

    private void showSeenMemosFragment() {
        mSeenMemosFragment = (SeenMemosFragment) mFragmentManager.findFragmentByTag(SeenMemosFragment.TAG);
        if (mSeenMemosFragment == null) {
            mSeenMemosFragment = SeenMemosFragment.getInstance();
            mSeenMemosPresenter = SeenMemosPresenter.getInstance(LocalMemoModel.getInstance(), mSeenMemosFragment);
            FragmentUtils.addfragment(mFragmentManager, mSeenMemosFragment, R.id.fl_content, SeenMemosFragment.TAG);
        } else {
            FragmentUtils.switchFragment(mFragmentManager, mFragment, mSeenMemosFragment, SeenMemosFragment.TAG, false);
            lastFragment = mFragment;
        }
        mFragment = mSeenMemosFragment;
        mPresenter = mSeenMemosPresenter;
    }

    private void showWantMemosFragment() {
        mWantMemosFragment = (WantMemosFragment) mFragmentManager.findFragmentByTag(WantMemosFragment.TAG);
        if (mWantMemosFragment == null) {
            mWantMemosFragment = WantMemosFragment.getInstance();
            mWantMemosPresenter = WantMemosPresenter.getInstance(LocalMemoModel.getInstance(), mWantMemosFragment);
        }
        FragmentUtils.switchFragment(mFragmentManager, mFragment, mWantMemosFragment, WantMemosFragment.TAG, false);
        lastFragment = mFragment;
        mFragment = mWantMemosFragment;
        mPresenter = mWantMemosPresenter;
    }

    private void showTagFragment() {
        mTagFragment = (TagFragment) mFragmentManager.findFragmentByTag(TagFragment.TAG);
        if (mTagFragment == null) {
            mTagFragment = TagFragment.getInstance();
            mTagPresenter = TagPresenter.getInstance(LocalMemoModel.getInstance(), mTagFragment);
        }
        FragmentUtils.switchFragment(mFragmentManager, mFragment, mTagFragment, TagFragment.TAG, false);
        lastFragment = mFragment;
        mFragment = mTagFragment;
        mPresenter = mTagPresenter;
    }

    private void showTopFragment() {
        mTopFragment = (TopFragment) mFragmentManager.findFragmentByTag(TopFragment.TAG);
        if (mTopFragment == null) {
            mTopFragment = TopFragment.getInstance();
            mTopPresenter = TopPresenter.getInstance(LocalMemoModel.getInstance(), mTopFragment);
        }
        FragmentUtils.switchFragment(mFragmentManager, mFragment, mTopFragment, TopFragment.TAG, false);
        lastFragment = mFragment;
        mFragment = mTopFragment;
        mPresenter = mTopPresenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mToolbar.setTitle(item.getTitle());
        AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        if (lp.getScrollFlags() == 0) {
            lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        switch (item.getItemId()) {
            case R.id.nav_item_seen:
                if (mFragment != mSeenMemosFragment) {
                    showSeenMemosFragment();
                    if (fab.getParent() == null) {
                        cl.addView(fab);
                    }
                }
                break;
            case R.id.nav_item_want:
                if (mFragment != mWantMemosFragment) {
                    showWantMemosFragment();
                    if (fab.getParent() == null) {
                        cl.addView(fab);
                    }
                }
                break;
            case R.id.nav_item_tag:
                if (mFragment != mTagFragment) {
                    showTagFragment();
                    if (fab.getParent() != null) {
                        cl.removeView(fab);
                    }
                }
                break;
            case R.id.nav_item_top:
                if (mFragment != mTopFragment) {
                    showTopFragment();
                    if (fab.getParent() != null) {
                        cl.removeView(fab);
                    }
                }
                break;
            case R.id.nav_item_settings:
                // TODO: 2016/10/15  打开settingsActivity
                Toast.makeText(this, "设置暂时没做", Toast.LENGTH_SHORT).show();
                break;
            //case R.id.nav_item_nightmode:
            // TODO: 2016/10/15  切换夜间模式
            //break;
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
            switch (lastFragment.getClass().getSimpleName()) {
                case SeenMemosFragment.TAG:
                    if (fab.getParent() == null) {
                        cl.addView(fab);
                    }
                    lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                            AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    mFragment = mSeenMemosFragment;
                    break;
                case WantMemosFragment.TAG:
                    if (fab.getParent() == null) {
                        cl.addView(fab);
                    }
                    lp.setScrollFlags(0);
                    mFragment = mWantMemosFragment;
                    break;
                case TagFragment.TAG:
                    if (fab.getParent() != null) {
                        cl.removeView(fab);
                    }
                    lp.setScrollFlags(0);
                    mFragment = mTagFragment;
                    break;
                case TopFragment.TAG:
                    if (fab.getParent() != null) {
                        cl.removeView(fab);
                    }
                    lp.setScrollFlags(0);
                    mFragment = mTopFragment;
                    break;
            }
            mToolbar.setLayoutParams(lp);
            super.onBackPressed();
        } else {
            exitBy2Click();
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


    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, R.string.exit_by_2_click_hint, Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
        }
    }
}
