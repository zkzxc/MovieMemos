package com.zk.moviememos.model;

import com.zk.moviememos.po.Memo;

import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface MemoModel {

    void loadMemos(LoadMemosCallback callback);

    interface LoadMemosCallback {

        void onMemosLoaded(List<Memo> memos);

        void onDataNotAvailable();
    }

}
