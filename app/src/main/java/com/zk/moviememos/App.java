package com.zk.moviememos;

import android.app.Application;

import com.zk.moviememos.util.LogUtils;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class App extends Application {

    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtils.setLogLevel(LogUtils.LEVEL_VERBOSE);
    }

    public static App getContext() {
        return mContext;
    }

}
