package com.zk.moviememos.presenter;

import android.os.Handler;
import android.os.Looper;

import com.zk.moviememos.App;
import com.zk.moviememos.constants.BusinessConstans;
import com.zk.moviememos.contract.SeenMemosContract;
import com.zk.moviememos.model.LocalMemoModel;
import com.zk.moviememos.model.MemoModel;
import com.zk.moviememos.util.LogUtils;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class SeenMemosPresenter implements SeenMemosContract.Presenter {

    private static MemoModel mMemoModel;
    private SeenMemosContract.View mView;
    private int offset;

    public static SeenMemosPresenter getInstance(MemoModel memoModel, SeenMemosContract.View view) {
        return new SeenMemosPresenter(memoModel, view);
    }

    private SeenMemosPresenter(MemoModel memoModel, SeenMemosContract.View view) {
        this.mMemoModel = memoModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void initOnCreate() {

    }

    @Override
    public void loadOnResume() {
        if (App.updateSeenMemo) {
            App.updateSeenMemo = false;
            offset = 0;
            mView.clear();
        }
        loadMemos(offset);
    }

    @Override
    public void loadOnHiddenChanged(boolean hidden) {

    }

    @Override
    public void loadMemos(final int offset) {
        this.offset = offset;
        mView.showProgress();
        mMemoModel.loadSeenMemos(null, BusinessConstans.PAGE_SIZE_SEEN_MEMOS, offset,
                LocalMemoModel.MOVIE_MEMO_COLUMN_VIEWING_DATE, BusinessConstans.ORDER_WAY_DESC,
                new MemoModel.LoadMemosCallback() {
                    @Override
                    public void onMemosLoaded(final List<SimpleMovieMemo> memos) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mView.isActive()) {
                                    mView.hideProgress();
                                    if (memos.isEmpty()) {
                                        if (offset == 0) {
                                            mView.showNoMemo();
                                        } else {
                                            mView.addFooter();
                                        }
                                    } else {
                                        mView.hideNoMemo();
                                        List<SimpleMovieMemo> headerMemos = addSections(memos,
                                                LocalMemoModel.MOVIE_MEMO_COLUMN_VIEWING_DATE);
                                        mView.showSeenMemos(headerMemos);
                                        //mView.showSeenMemos(memos);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
    }

    private List<SimpleMovieMemo> addSections(List<SimpleMovieMemo> memos, String orderBy) {
        List<SimpleMovieMemo> sectionMemos = new ArrayList<>();
        String sectionText = null;
        SimpleMovieMemo lastSectionMemo = null;     // 记录上一个sectionMemo
        int sectionTextRight = 0;   // section右侧的文字，用来记录该section中有几个item
        for (int i = 0; i < memos.size(); i++) {
            SimpleMovieMemo memo = memos.get(i);
            memo.setViewType(SimpleMovieMemo.VIEW_TYPE_ITEM);
            sectionTextRight++;
            if (orderBy == LocalMemoModel.MOVIE_MEMO_COLUMN_VIEWING_DATE) {
                Date viewingDate = memo.getViewingDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(viewingDate);
                String viewingYear = String.valueOf(calendar.get(Calendar.YEAR));
                if (!viewingYear.equals(sectionText)) {
                    if (i != 0) {
                        // 设置上一个section的sectionTextRight
                        lastSectionMemo.setSectionTextRight(String.valueOf(--sectionTextRight));
                        sectionTextRight = 1;
                    }
                    SimpleMovieMemo sectionMemo = new SimpleMovieMemo();
                    lastSectionMemo = sectionMemo;
                    sectionText = viewingYear;
                    sectionMemo.setViewType(SimpleMovieMemo.VIEW_TYPE_SECTION);
                    sectionMemo.setSectionTextLeft(sectionText);
                    sectionMemos.add(sectionMemo);
                }
            }
            sectionMemos.add(memo);
        }
        lastSectionMemo.setSectionTextRight(String.valueOf(sectionTextRight)); // 设置最后一个section的sectionTextRight
        LogUtils.d(this, "memos' size after addSection:    " + sectionMemos.size());
        return sectionMemos;
    }
}
