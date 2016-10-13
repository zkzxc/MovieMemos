package com.zk.moviememos.model;

import com.zk.moviememos.po.DoubanMovie;
import com.zk.moviememos.po.Memo;
import com.zk.moviememos.vo.SimpleMovieMemo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MemoModel {

    void saveMemo(DoubanMovie movie, Memo memo);

    void loadSeenMemos(SimpleMovieMemo simpleMovieMemo, int pageSize, int offset, String orderBy, String orderWay,
                       LoadMemosCallback callback);

    void findMemosByMovieId(String movieId, LoadMemosCallback callback);

    void findMemoByMemoId(String memoId, LoadMemoCallback callback);

    void deleteMemoById(String memoId);

    interface LoadMemosCallback {

        void onMemosLoaded(List<SimpleMovieMemo> memos);

        void onDataNotAvailable();
    }

    interface LoadMemoCallback {

        void onMemoLoaded(Memo memo);

        void onDataNotAvailable();
    }

}
