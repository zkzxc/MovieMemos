package com.zk.moviememos.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.contract.MovieContract;
import com.zk.moviememos.databinding.ActivityMovieBinding;
import com.zk.moviememos.model.DoubanMovieModel;
import com.zk.moviememos.model.LocalMemoModel;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.presenter.AddEditMemoPresenter;
import com.zk.moviememos.presenter.MemoDetailPresenter;
import com.zk.moviememos.presenter.MovieDetailPresenter;
import com.zk.moviememos.presenter.MoviePresenter;
import com.zk.moviememos.util.BitmapUtils;
import com.zk.moviememos.util.DisplayUtils;
import com.zk.moviememos.util.FragmentUtils;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.view.fragment.AddEditMemoFragment;
import com.zk.moviememos.view.fragment.BaseFragment;
import com.zk.moviememos.view.fragment.MemoDetailFragment;
import com.zk.moviememos.view.fragment.MovieDetailFragment;

import java.io.File;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieActivity extends AppCompatActivity implements MovieContract.View {

    public static final String MOVIE_DETAIL_FRAGMENT_TAG = "movie_detail_fragment_tag";
    public static final String ADD_EDIT_MEMO_FRAGMENT_TAG = "add_edit_memo_fragment_tag";
    public static final String MEMO_DETAIL_FRAGMENT_TAG = "memo_detail_fragment_tag";

    private MovieContract.Presenter mPresenter;
    private boolean mShown;
    private ActivityMovieBinding binding;

    private DoubanMovie mDoubanMovie;
    private Drawable mediumPoster;

    private FragmentManager fragmentManager;
    private BaseFragment mFramgent;
    private BaseFragment lastFragment;
    private MovieDetailFragment movieDetailFragment;
    private AddEditMemoFragment addEditMemoFragment;
    private MemoDetailFragment memoDetailFragment;
    private MovieDetailPresenter movieDetailPresenter;
    private AddEditMemoPresenter addEditMemoPresenter;
    private MemoDetailPresenter memoDetailPresenter;

    private FrameLayout flProgress;
    private ObjectAnimator mProgressAnimator;

    private String movieId;
    private String action;
    private boolean isTv;
    private String memoId;

    private Memo oldMemo;
    private Memo newMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        movieId = bundle.getString("movieId");
        action = bundle.getString("action");
        isTv = bundle.getBoolean("isTv");
        memoId = bundle.getString("memoId");
        String title = bundle.getString("title");
        String posterUrl = bundle.getString("posterUrl");
        binding.collapsingToolbar.setTitle(title);

        initPoster(posterUrl);

        if (savedInstanceState == null) {
            if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MOVIE_DETAIL.equals(action)) {
                showMovieDetailFragment();
            } else if (BusinessConstans.MOVIE_ACTIVITY_ACTION_ADD_MEMO.equals(action)) {
                showAddEditMemoFragment();
            } else if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MEMO.equals(action)) {
                showMemoDetailFragment();
            }
        }
        if (mFramgent == movieDetailFragment) {
            binding.llMovieDetailBtns.setVisibility(View.VISIBLE);
            binding.llAddEditMemoBtns.setVisibility(View.GONE);
            binding.llMemoDetailBtns.setVisibility(View.GONE);
        } else if (mFramgent == addEditMemoFragment) {
            binding.llMovieDetailBtns.setVisibility(View.GONE);
            binding.llAddEditMemoBtns.setVisibility(View.VISIBLE);
            binding.llMemoDetailBtns.setVisibility(View.GONE);
        } else if (mFramgent == memoDetailFragment) {
            binding.llMovieDetailBtns.setVisibility(View.GONE);
            binding.llAddEditMemoBtns.setVisibility(View.GONE);
            binding.llMemoDetailBtns.setVisibility(View.VISIBLE);
        }

        flProgress = binding.flProgress;
        ImageView ivProgress = binding.ivProgress;
        mProgressAnimator = ObjectAnimator.ofFloat(ivProgress, "rotation", 0f, 360f);
        mProgressAnimator.setDuration(1000);
        mProgressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mProgressAnimator.setInterpolator(new LinearInterpolator());

        binding.btnAddToSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditMemoFragment();
                binding.llMovieDetailBtns.setVisibility(View.GONE);
                binding.llAddEditMemoBtns.setVisibility(View.VISIBLE);
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMemo = null;
                MovieActivity.this.onBackPressed();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addEditMemoPresenter.saveMemo(mDoubanMovie, newMemo);
                    BitmapUtils.saveBitmapToCacheDir(MovieActivity.this,
                            ((BitmapDrawable)(binding.ivMovieImageLarge.getDrawable())).getBitmap(), movieId + ".jpg");
                    BitmapUtils.saveBitmapToCacheDir(MovieActivity.this,
                            ((BitmapDrawable)(binding.ivMovieImageLargeBlur.getDrawable())).getBitmap(), "B" + movieId + ".jpg");
                    App.updateSeenMemo = true;
                    Toast.makeText(MovieActivity.this, ResourseUtils.getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();
                    MovieActivity.this.onBackPressed();
                } catch (Exception e) {
                    Toast.makeText(MovieActivity.this, ResourseUtils.getString(R.string.save_failure),
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        binding.btnMovieDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovieDetailFragment();
                binding.llMemoDetailBtns.setVisibility(View.GONE);
                binding.llMovieDetailBtns.setVisibility(View.VISIBLE);
            }
        });
        binding.btnEditMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditMemoFragment();
                binding.llMemoDetailBtns.setVisibility(View.GONE);
                binding.llAddEditMemoBtns.setVisibility(View.VISIBLE);
            }
        });
        binding.btnDeleteMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MovieActivity.this);
                builder.setMessage(R.string.delete_memo_message);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memoDetailPresenter.deleteMemo(memoId);
                        App.updateSeenMemo = true;
                        Toast.makeText(MovieActivity.this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        MovieActivity.this.finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        MoviePresenter moviePresenter = MoviePresenter.getInstance(DoubanMovieModel.getInstance(), this, movieId);
        moviePresenter.getMovie(action);
    }

    private void initPoster(String posterUrl) {
        if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MEMO.equals(action)) {
            final File posterFile = new File(getFilesDir(), movieId + ".jpg");
            final File blurredPosterFile = new File(getFilesDir(), "B" + movieId + ".jpg");
            if (posterFile.exists() && blurredPosterFile.exists()) {
                Glide.with(this).load(posterFile).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LogUtils.d(this, "从缓存读取图片：   " + posterFile.getPath());
                        binding.ivMovieImageLarge.setImageBitmap(resource);
                    }
                });
                Glide.with(this).load(blurredPosterFile).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LogUtils.d(this, "从缓存读取图片：   " + blurredPosterFile.getPath());
                        binding.ivMovieImageLargeBlur.setImageBitmap(resource);
                        revealAnim(binding.ivMovieImageLargeBlur);
                    }
                });
            } else {
                loadPosterFromWeb(posterUrl);
            }
        }
        loadPosterFromWeb(posterUrl);
    }

    private void loadPosterFromWeb(String PosterUrl) {
        Glide.with(this).load(PosterUrl).asBitmap().priority(Priority.HIGH).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mediumPoster = new BitmapDrawable(resource);
                binding.ivMovieImageLarge.setImageBitmap(resource);
                Bitmap blurredPoster = BitmapUtils.doBlur(resource, 40, false);
                blurredPoster = BitmapUtils.setBrightness(blurredPoster, -40);
                final ImageView ivBlurredPoster = binding.ivMovieImageLargeBlur;
                ivBlurredPoster.setImageBitmap(blurredPoster);
                revealAnim(ivBlurredPoster);
            }
        });
    }

    private void revealAnim(final ImageView ivBlurredPoster) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ivBlurredPoster.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int
                        oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    float radius = (float) Math.hypot(ivBlurredPoster.getWidth(), ivBlurredPoster.getHeight());
                    Animator reveal = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        reveal = ViewAnimationUtils.createCircularReveal(ivBlurredPoster, 0, DisplayUtils
                                .dip2px(MovieActivity.this, 340), 0, radius);
                    }
                    reveal.setDuration(500);
                    reveal.setInterpolator(new AccelerateInterpolator());
                    reveal.start();
                    reveal.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            });
        }
    }

    private void showMovieDetailFragment() {
        movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentByTag(MOVIE_DETAIL_FRAGMENT_TAG);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.getInstance();
            movieDetailPresenter = MovieDetailPresenter.getInstance(LocalMemoModel.getInstance(),
                    movieDetailFragment, movieId);
            if (mFramgent == null) {
                FragmentUtils.addfragment(fragmentManager, movieDetailFragment, R.id.fl_movie_detail,
                        MOVIE_DETAIL_FRAGMENT_TAG);
            } else {
                FragmentUtils.switchFragment(fragmentManager, mFramgent, movieDetailFragment,
                        MOVIE_DETAIL_FRAGMENT_TAG, true);
                lastFragment = mFramgent;
                if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MEMO.equals(action)) {
                    movieDetailPresenter.getMemosByMovieId(mDoubanMovie.getId());
                    movieDetailFragment.showMovieDetail(mDoubanMovie);
                }
            }
        } else {
            FragmentUtils.switchFragment(fragmentManager, mFramgent, movieDetailFragment,
                    MOVIE_DETAIL_FRAGMENT_TAG, true);
            lastFragment = mFramgent;
        }
        mFramgent = movieDetailFragment;
    }

    private void showAddEditMemoFragment() {
        addEditMemoFragment = (AddEditMemoFragment) fragmentManager.findFragmentByTag
                (ADD_EDIT_MEMO_FRAGMENT_TAG);
        if (addEditMemoFragment == null) {
            if (oldMemo == null) {
                newMemo = new Memo();
            } else {
                newMemo = (Memo) oldMemo.clone();
            }
            addEditMemoFragment = AddEditMemoFragment.getInstance(isTv);
            addEditMemoPresenter = AddEditMemoPresenter.getInstance(LocalMemoModel.getInstance(),
                    addEditMemoFragment, memoId);
            if (mFramgent == null) {
                FragmentUtils.addfragment(fragmentManager, addEditMemoFragment, R.id.fl_movie_detail,
                        ADD_EDIT_MEMO_FRAGMENT_TAG);
            } else {
                addEditMemoFragment.setTargetFragment(mFramgent, 0);
                FragmentUtils.switchFragment(fragmentManager, mFramgent, addEditMemoFragment,
                        ADD_EDIT_MEMO_FRAGMENT_TAG, true);
                lastFragment = mFramgent;
            }
        } else {
            FragmentUtils.switchFragment(fragmentManager, mFramgent, addEditMemoFragment,
                    ADD_EDIT_MEMO_FRAGMENT_TAG, true);
            lastFragment = mFramgent;
        }
        mFramgent = addEditMemoFragment;
    }

    private void showMemoDetailFragment() {
        memoDetailFragment = (MemoDetailFragment) fragmentManager.findFragmentByTag(MEMO_DETAIL_FRAGMENT_TAG);
        if (memoDetailFragment == null) {
            memoDetailFragment = MemoDetailFragment.getInstance();
            memoDetailPresenter = MemoDetailPresenter.getInstance(LocalMemoModel.getInstance(),
                    memoDetailFragment, memoId);
            if (mFramgent == null) {
                FragmentUtils.addfragment(fragmentManager, memoDetailFragment, R.id.fl_movie_detail,
                        MEMO_DETAIL_FRAGMENT_TAG);
            } else {
                FragmentUtils.switchFragment(fragmentManager, mFramgent, memoDetailFragment,
                        MEMO_DETAIL_FRAGMENT_TAG, true);
                lastFragment = mFramgent;
            }
        } else {
            FragmentUtils.switchFragment(fragmentManager, mFramgent, memoDetailFragment,
                    MEMO_DETAIL_FRAGMENT_TAG, true);
            lastFragment = mFramgent;
        }
        mFramgent = memoDetailFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        // 由于使用Glide会默认添加一个SupportRequestManagerFragment，所以再添加一个Fragment时会有两个Fragmnet
        if (fragmentManager.getFragments().size() > 2) {
            if (mFramgent == addEditMemoFragment) {
                binding.llAddEditMemoBtns.setVisibility(View.GONE);
                if (lastFragment == movieDetailFragment) {
                    binding.llMovieDetailBtns.setVisibility(View.VISIBLE);
                } else if (lastFragment == memoDetailFragment) {
                    binding.llMemoDetailBtns.setVisibility(View.VISIBLE);
                }
                BaseFragment temp = mFramgent;
                mFramgent = lastFragment;
                lastFragment = temp;
            } else if (mFramgent == movieDetailFragment) {
                binding.llMovieDetailBtns.setVisibility(View.GONE);
                binding.llMemoDetailBtns.setVisibility(View.VISIBLE);
                mFramgent = memoDetailFragment;
                lastFragment = movieDetailFragment;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void showMovie(DoubanMovie doubanMovie) {
        LogUtils.d(this, "showMovie");
        LogUtils.d(this, doubanMovie.getTitle());
        binding.llMovieIntro.setVisibility(View.VISIBLE);
        binding.flMovieDetail.setVisibility(View.VISIBLE);
        mDoubanMovie = doubanMovie;
        binding.setDoubanMovie(doubanMovie);
        //Glide.with(MovieActivity.this).load(doubanMovie.getImages().getLarge()).priority(Priority.NORMAL)
        //        .placeholder(mediumPoster).dontAnimate().into(binding.ivMovieImageLarge);
        if (BusinessConstans.MOVIE_ACTIVITY_ACTION_SHOW_MOVIE_DETAIL.equals(action)) {
            movieDetailPresenter.getMemosByMovieId(doubanMovie.getId());
            movieDetailFragment.showMovieDetail(doubanMovie);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mShown = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mShown = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        if (isActive()) {
            if (!flProgress.isShown()) {
                flProgress.setVisibility(View.VISIBLE);
            }
            mProgressAnimator.start();
        }
    }

    @Override
    public void hideProgress() {
        if (isActive()) {
            if (mProgressAnimator.isRunning()) {
                mProgressAnimator.end();
            }
            if (flProgress.isShown()) {
                flProgress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean isActive() {
        return mShown;
    }

    public Memo getMemo() {
        return newMemo;
    }

    public void setOldMemo(Memo memo) {
        this.oldMemo = memo;
    }

}
