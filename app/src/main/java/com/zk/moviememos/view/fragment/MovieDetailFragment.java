package com.zk.moviememos.view.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zk.moviememos.App;
import com.zk.moviememos.R;
import com.zk.moviememos.contract.MovieDetailContract;
import com.zk.moviememos.databinding.FragmentMovieDetailBinding;
import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.util.ResourseUtils;
import com.zk.moviememos.view.Adapter.SimpleMovieMemoSlimAdapter;
import com.zk.moviememos.vo.SimpleMovieMemo;
import com.zk.moviememos.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class MovieDetailFragment extends BaseFragment<MovieDetailContract.Presenter> implements MovieDetailContract
        .View {

    private FragmentMovieDetailBinding mBinding;

    private SimpleMovieMemoSlimAdapter memoAdapter;

    private boolean expandFlag;

    public static MovieDetailFragment getInstance() {
        MovieDetailFragment fragment = new MovieDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false);

        ViewGroup.LayoutParams layoutParams = mBinding.tvSummary.getLayoutParams();
        layoutParams.height = getShortMeasureHeight();
        mBinding.tvSummary.setLayoutParams(layoutParams);
        mBinding.llSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandAndCollapse();
            }
        });

        RecyclerView rvMovieMemos = mBinding.rvMovieMemos;
        memoAdapter = new SimpleMovieMemoSlimAdapter(mActivity, new ArrayList<SimpleMovieMemo>(0));
        rvMovieMemos.setAdapter(memoAdapter);
        rvMovieMemos.setItemAnimator(new DefaultItemAnimator());
        rvMovieMemos.setLayoutManager(new LinearLayoutManager(mActivity));
        rvMovieMemos.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));

        return mBinding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void showMovieDetail(final DoubanMovie doubanMovie) {
        if (mBinding != null) {
            showCelebreties(doubanMovie);
            mBinding.setDoubanMovie(doubanMovie);
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showCelebreties(doubanMovie);
                    mBinding.setDoubanMovie(doubanMovie);
                }
            });
        }
    }

    @Override
    public void showMemos(List<SimpleMovieMemo> memos) {
        if (memos != null && memos.size() > 0) {
            mBinding.tvMemosCount.setText(memos.size() +
                    ResourseUtils.getString(R.string.tiao) +
                    ResourseUtils.getString(R.string.movie_memo));
            memoAdapter.setList(memos);
        } else {
            mBinding.llMovieMemos.setVisibility(View.GONE);
        }
    }

    private void showCelebreties(DoubanMovie movie) {
        LinearLayout llCelebrities = mBinding.llCelebrities;
        if (movie.getDirectors() != null) {
            for (int i = 0; i < movie.getDirectors().size(); i++) {
                View celebrityItem = View.inflate(mActivity, R.layout.celebrity_item, null);
                TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                if (i == 0) {
                    tvCelebrityTag.setText(R.string.director);
                }
                ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                if (movie.getDirectors().get(i).getAvatars() != null) {
                    Glide.with(mActivity).load(movie.getDirectors().get(i).getAvatars().getMedium()).centerCrop()
                            .into(ivCelebrityImg);
                }
                TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                tvCelebrityName.setText(movie.getDirectors().get(i).getName());
                llCelebrities.addView(celebrityItem);
            }
        }
        if (movie.getCasts() != null) {
            for (int i = 0; i < movie.getCasts().size(); i++) {
                View celebrityItem = View.inflate(mActivity, R.layout.celebrity_item, null);
                TextView tvCelebrityTag = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_tag);
                if (i == 0) {
                    tvCelebrityTag.setText(R.string.cast);
                }
                ImageView ivCelebrityImg = (ImageView) celebrityItem.findViewById(R.id.iv_celebrity_img);
                if (movie.getCasts().get(i).getAvatars() != null) {
                    Glide.with(mActivity).load(movie.getCasts().get(i).getAvatars().getMedium()).centerCrop()
                            .into(ivCelebrityImg);
                }
                TextView tvCelebrityName = (TextView) celebrityItem.findViewById(R.id.tv_celebrity_name);
                tvCelebrityName.setText(movie.getCasts().get(i).getName());
                llCelebrities.addView(celebrityItem);
            }
        }
    }

    // 简介TextView的展开和收起
    private void expandAndCollapse() {
        final TextView tvSummary = mBinding.tvSummary;
        final ImageView ivExpand = mBinding.ivExpand;
        final ViewGroup.LayoutParams layoutParams = tvSummary.getLayoutParams();
        final NestedScrollView nestedScrollView = getNestedScrollView(tvSummary);
        int startHeight;
        int endHeight;
        float startRotation;
        float endRotation;
        if (!expandFlag) {
            startHeight = getShortMeasureHeight();
            endHeight = getLongMeasureHeight();
            startRotation = 0f;
            endRotation = 180f;
        } else {
            startHeight = getLongMeasureHeight();
            endHeight = getShortMeasureHeight();
            startRotation = 180f;
            endRotation = 0f;
        }
        expandFlag = !expandFlag;
        ValueAnimator heightAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                tvSummary.setLayoutParams(layoutParams);
                nestedScrollView.scrollTo(0, nestedScrollView.getMeasuredHeight());
            }
        });
        heightAnimator.setDuration(300);
        heightAnimator.start();
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(ivExpand, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.start();
    }

    // 获取简介TextView的最小高高度
    private int getShortMeasureHeight() {
        TextView tvSummary = mBinding.tvSummary;
        TextView tv = new TextView(App.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tv.setLines(5);
        tv.setLineSpacing(0f, 1.3f);
        int width = tvSummary.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        tv.measure(widthMeasureSpec, heightMeasureSpec);
        return tv.getMeasuredHeight();
    }

    // 获取简介TextVIew的最大高度
    private int getLongMeasureHeight() {
        TextView tvSummary = mBinding.tvSummary;
        int width = tvSummary.getMeasuredWidth();
        tvSummary.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(5000, View.MeasureSpec.AT_MOST);
        tvSummary.measure(widthMeasureSpec, heightMeasureSpec);
        return tvSummary.getMeasuredHeight();
    }

    // 递归向上找NestedScrollView
    private NestedScrollView getNestedScrollView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            if (group instanceof NestedScrollView) {
                return (NestedScrollView) group;
            } else {
                return getNestedScrollView(group);
            }
        } else {
            return null;
        }
    }
}
