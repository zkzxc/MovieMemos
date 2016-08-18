package com.zk.moviememos.util;

import android.content.res.Resources;

import com.zk.moviememos.App;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class ResourseUtils {

    private static Resources mResources = App.getContext().getResources();

    public static String getString(int resId) {
        return getResourse().getString(resId);
    }

    private static Resources getResourse() {
        return mResources;
    }
}
