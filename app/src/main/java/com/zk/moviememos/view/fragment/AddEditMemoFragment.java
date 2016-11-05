package com.zk.moviememos.view.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.zk.moviememos.R;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.contract.AddEditMemoContract;
import com.zk.moviememos.databinding.FragmentAddEditMemoBinding;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.view.activity.MovieActivity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class AddEditMemoFragment extends BaseFragment<AddEditMemoContract.Presenter> implements AddEditMemoContract
        .View {

    public static final String IS_TV = "is_tv";

    private FragmentAddEditMemoBinding mBinding;

    private boolean isTv;

    private Memo memo;

    private boolean expandFlag;
    private int totalScore;
    private float averageScore;
    private boolean rbReady;

    public static AddEditMemoFragment getInstance(boolean isTV) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_TV, isTV);
        AddEditMemoFragment fragment = new AddEditMemoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isTv = bundle.getBoolean(IS_TV);
        }
        if (savedInstanceState == null) {
            memo = ((MovieActivity) mActivity).getMemo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentAddEditMemoBinding.inflate(inflater, container, false);

        // 电影和电视剧不同的控件
        if (isTv) {
            initTvMemoView();
        } else {
            initMovieMemoView();
        }

        // 电影和电视剧相同的控件
        // 观影日期内容
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (memo.getMovieMemoId() == null) {
            memo.setViewingDate(calendar.getTimeInMillis());
        }
        showDate(year, month, day);
        mBinding.tvViewingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(year, month, day);
            }
        });

        // 评分
        mBinding.llRateExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandAndCollapse();
            }
        });
        mBinding.rbAverageScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rbReady) {
                    averageScore = rating * 2;
                    mBinding.tvAverageScore.setText(String.valueOf(averageScore));
                    memo.setAverageScore(averageScore);
                    if (fromUser) {
                        if (mBinding.rbStoryScore.getRating() != 0) {
                            mBinding.rbStoryScore.setRating(0);
                            memo.setStoryScore(null);
                        }
                        if (mBinding.rbVisualScore.getRating() != 0) {
                            mBinding.rbVisualScore.setRating(0);
                            memo.setVisualScore(null);
                        }
                        if (mBinding.rbAuralScore.getRating() != 0) {
                            mBinding.rbAuralScore.setRating(0);
                            memo.setAuralScore(null);
                        }
                        mBinding.rbAverageScore.setRating(rating);
                    }
                }
            }
        });
        mBinding.rbStoryScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            int storyScore;

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rbReady) {
                    int oldStoryScore = 0;
                    if (storyScore != 0) {
                        oldStoryScore = storyScore;
                        storyScore = 0;
                    }
                    storyScore = (int) (rating * 2);
                    mBinding.tvStoryScore.setText(String.valueOf(storyScore));
                    memo.setStoryScore(storyScore);
                    totalScore = totalScore - oldStoryScore + storyScore;
                    mBinding.rbAverageScore.setRating(devide(totalScore, 6));
                }
            }
        });
        mBinding.rbVisualScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            int visualScore;

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rbReady) {
                    int oldVisualScore = 0;
                    if (visualScore != 0) {
                        oldVisualScore = visualScore;
                        visualScore = 0;
                    }
                    visualScore = (int) (rating * 2);
                    mBinding.tvVisualScore.setText(String.valueOf(visualScore));
                    memo.setVisualScore(visualScore);
                    totalScore = totalScore - oldVisualScore + visualScore;
                    mBinding.rbAverageScore.setRating(devide(totalScore, 6));
                }
            }
        });
        mBinding.rbAuralScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            int auralScore;

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rbReady) {
                    int oldAuralScore = 0;
                    if (auralScore != 0) {
                        oldAuralScore = auralScore;
                        auralScore = 0;
                    }
                    auralScore = (int) (rating * 2);
                    mBinding.tvAuralScore.setText(String.valueOf(auralScore));
                    memo.setAuralScore(auralScore);
                    totalScore = totalScore - oldAuralScore + auralScore;
                    mBinding.rbAverageScore.setRating(devide(totalScore, 6));
                }
            }
        });

        mBinding.etShortComment.addTextChangedListener(new TextWatcher() {
            CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                memo.setShortComment(temp.toString());
            }
        });

        LogUtils.d(this, memo.toString());
        if (memo.getMovieMemoId() != null) {
            showOldMemo(memo);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = mBinding.llRate.getLayoutParams();
                layoutParams.height = getShortMeasureHeight();
                mBinding.llRate.setLayoutParams(layoutParams);
                rbReady = true;
            }
        });
    }

    // 初始化电视剧独有的控件
    private void initTvMemoView() {
        // 观看日期的标题和提醒
        mBinding.tvViewingDateTitle.setText(R.string.viewing_date_tv);
        mBinding.tvViewingDate.setHint(R.string.viewing_date_hint_tv);
        // 观看方式
        mBinding.tvViewingWayTitle.setText(R.string.viewing_way_tv);
        ArrayAdapter<String> viewingWayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.viewing_way_tv));
        viewingWayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spViewingWay.setAdapter(viewingWayAdapter);
        mBinding.spViewingWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        memo.setViewingWay(BusinessConstans.VIEWING_WAY_ONLINE);
                        break;
                    case 1:
                        memo.setViewingWay(BusinessConstans.VIEWING_WAY_DOWNLOAD);
                        break;
                    case 2:
                        memo.setViewingWay(BusinessConstans.VIEWING_WAY_TV);
                        break;
                    case 3:
                        memo.setViewingWay(BusinessConstans.VIEWING_WAY_DVD);
                        break;
                    case 4:
                        memo.setViewingWay(BusinessConstans.VIEWING_WAY_OTHER);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 隐藏观影版本、电影版本、观影状态
        mBinding.llViewingVersion.setVisibility(View.GONE);
        mBinding.llMovieVersion.setVisibility(View.GONE);
        mBinding.llViewingMood.setVisibility(View.GONE);
    }

    // 初始化电影独有的控件
    private void initMovieMemoView() {
        // 观影方式
        ArrayAdapter<String> viewingWayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.viewing_way));
        viewingWayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spViewingWay.setAdapter(viewingWayAdapter);
        mBinding.spViewingWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 5) {
                    memo.setViewingWay(String.valueOf(position + 1));
                } else {
                    memo.setViewingWay(BusinessConstans.VIEWING_WAY_OTHER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 观影版本1
        ArrayAdapter<String> viewingVersion1Adapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.viewing_version_1));
        viewingVersion1Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mBinding.spViewingVersion1.setAdapter(viewingVersion1Adapter);
        mBinding.spViewingVersion1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 3) {
                    memo.setViewingVersion1(String.valueOf(position + 1));
                } else {
                    memo.setViewingVersion1(BusinessConstans.VIEWING_VERSION_OTHER1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 观影版本2
        ArrayAdapter<String> viewingVersion2Adapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.viewing_version_2));
        viewingVersion2Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mBinding.spViewingVersion2.setAdapter(viewingVersion2Adapter);
        mBinding.spViewingVersion2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 2) {
                    memo.setViewingVersion2(String.valueOf(position + 1));
                } else {
                    memo.setViewingVersion2(BusinessConstans.VIEWING_VERSION_OTHER2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 电影版本
        ArrayAdapter<String> movieVersionAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.movie_version));
        movieVersionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mBinding.spMovieVersion.setAdapter(movieVersionAdapter);
        mBinding.spMovieVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 3) {
                    memo.setMovieVersion(String.valueOf(position + 1));
                } else {
                    memo.setMovieVersion(BusinessConstans.MOVIE_VERSION_OTHER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 观影状态
        if (memo.getMovieMemoId() == null) {
            memo.setViewingMood(BusinessConstans.VIEWING_MOOD_AVERAGE);
        }
        mBinding.rgViewingMood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mood_good:
                        memo.setViewingMood(BusinessConstans.VIEWING_MOOD_GOOD);
                        break;
                    case R.id.rb_mood_average:
                        memo.setViewingMood(BusinessConstans.VIEWING_MOOD_AVERAGE);
                        break;
                    case R.id.rb_mood_bad:
                        memo.setViewingMood(BusinessConstans.VIEWING_MOOD_BAD);
                        break;
                }
            }
        });
    }

    // 选取日期，传入初始化的日期
    private void pickDate(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                memo.setViewingDate(new Date(year - 1900, monthOfYear, dayOfMonth).getTime());
                showDate(year, monthOfYear, dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showDate(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(getResources().getString(R.string.year))
                .append(month + 1).append(getResources().getString(R.string.month))
                .append(day).append(getResources().getString(R.string.day));
        mBinding.tvViewingDate.setText(sb.toString());
    }

    // 分项评分的展开和收起
    private void expandAndCollapse() {
        rbReady = false;
        final LinearLayout llRate = mBinding.llRate;
        final ImageView ivExpand = mBinding.ivExpand;
        final ViewGroup.LayoutParams layoutParams = llRate.getLayoutParams();
        int startHeight;
        int endHeight;
        float startRotation;
        float endRotation;
        if (!expandFlag) {
            startHeight = getShortMeasureHeight();
            endHeight = getLongMeasureHeight();
            startRotation = 0f;
            endRotation = 180f;
            mBinding.rbAverageScore.setStepSize(0.1f);
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
                llRate.setLayoutParams(layoutParams);
            }
        });
        heightAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rbReady = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        heightAnimator.setDuration(300);
        heightAnimator.start();
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(ivExpand, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.start();
    }

    // 获取评分LinearLayout的最小高高度
    private int getShortMeasureHeight() {
        RelativeLayout rlAverageScore = mBinding.rlAverageScore;
        int width = rlAverageScore.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        rlAverageScore.measure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.d(this, "short:   " + rlAverageScore.getMeasuredHeight());
        return rlAverageScore.getMeasuredHeight();
    }

    // 获取评分LinearLayout的最大高度
    private int getLongMeasureHeight() {
        LinearLayout llRate = mBinding.llRate;
        int width = llRate.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(5000, View.MeasureSpec.AT_MOST);
        llRate.measure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.d(this, "long:   " + llRate.getMeasuredHeight());
        return llRate.getMeasuredHeight();
    }

    // 除法，精确到小数点后一位
    private Float devide(float num, int by) {
        Double average = num / Double.valueOf(by);
        BigDecimal bigDecimal = new BigDecimal(average);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    public void showMemo(Memo memo) {
        mBinding.setMemo(memo);
    }

    private void showOldMemo(Memo memo) {
        mBinding.tvViewingDate.setText(memo.getViewingDateStr());
        mBinding.rbAverageScore.setRating(memo.getAverageScore());
        mBinding.tvAverageScore.setText(memo.getAverageScoreStr());
        mBinding.rbStoryScore.setRating(memo.getStoryScore());
        mBinding.tvStoryScore.setText(memo.getStoryScoreStr());
        mBinding.rbVisualScore.setRating(memo.getVisualScore());
        mBinding.tvVisualScore.setText(memo.getVisualScoreStr());
        mBinding.rbAuralScore.setRating(memo.getAuralScore());
        mBinding.tvAuralScore.setText(memo.getAuralScoreStr());
        if (memo.getShortComment() != null) {

            mBinding.etShortComment.setText(memo.getShortComment());
        } else {
            mBinding.etShortComment.setHint(R.string.no_short_comment);
        }
        if (isTv) {
            switch (memo.getViewingWay()) {
                case BusinessConstans.VIEWING_WAY_ONLINE:
                    mBinding.spViewingWay.setSelection(0);
                    break;
                case BusinessConstans.VIEWING_WAY_DOWNLOAD:
                    mBinding.spViewingWay.setSelection(1);
                    break;
                case BusinessConstans.VIEWING_WAY_TV:
                    mBinding.spViewingWay.setSelection(2);
                    break;
                case BusinessConstans.VIEWING_WAY_DVD:
                    mBinding.spViewingWay.setSelection(3);
                    break;
                case BusinessConstans.VIEWING_WAY_OTHER:
                    mBinding.spViewingWay.setSelection(4);
                    break;
            }
        } else {
            if (BusinessConstans.VIEWING_WAY_OTHER.equals(memo.getViewingWay())) {
                mBinding.spViewingWay.setSelection(5);
            } else {
                mBinding.spViewingWay.setSelection(Integer.valueOf(memo.getViewingWay()) - 1);
            }
            if (BusinessConstans.VIEWING_VERSION_OTHER1.equals(memo.getViewingVersion1())) {
                mBinding.spViewingVersion1.setSelection(3);
            } else {
                mBinding.spViewingVersion1.setSelection(Integer.valueOf(memo.getViewingVersion1()) - 1);
            }
            if (BusinessConstans.VIEWING_VERSION_OTHER2.equals(memo.getViewingVersion2())) {
                mBinding.spViewingVersion2.setSelection(2);
            } else {
                mBinding.spViewingVersion2.setSelection(Integer.valueOf(memo.getViewingVersion2()) - 1);
            }
            if (BusinessConstans.MOVIE_VERSION_OTHER.equals(memo.getMovieVersion())) {
                mBinding.spMovieVersion.setSelection(3);
            } else {
                mBinding.spMovieVersion.setSelection(Integer.valueOf(memo.getMovieVersion()) - 1);
            }
            switch (memo.getViewingMood()) {
                case BusinessConstans.VIEWING_MOOD_GOOD:
                    mBinding.rgViewingMood.check(R.id.rb_mood_good);
                    break;
                case BusinessConstans.VIEWING_MOOD_AVERAGE:
                    mBinding.rgViewingMood.check(R.id.rb_mood_average);
                    break;
                case BusinessConstans.VIEWING_MOOD_BAD:
                    mBinding.rgViewingMood.check(R.id.rb_mood_bad);
                    break;
            }
        }

    }
}
