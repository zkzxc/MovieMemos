package com.zk.moviememos.model;

import com.zk.moviememos.po.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class LocalMemoModel implements MemoModel {

    private static LocalMemoModel mModel;

    private LocalMemoModel(){}

    public static LocalMemoModel getInstance(){
        if (mModel == null) {
            mModel = new LocalMemoModel();
        }
        return mModel;
    }

    @Override
    public void loadMemos(LoadMemosCallback callback) {
        List<Memo> memos = new ArrayList<>();

        // TODO: 2016/7/16 查询Memo
        callback.onMemosLoaded(memos);
    }
}
