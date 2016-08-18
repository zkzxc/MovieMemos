package com.zk.moviememos.util;

import android.util.Log;

/**
 * 日志工具类
 * 设置好日志的输出级别限制后，可以指定某一个级别以上的日志才可以输出
 * 每一级日志输出方法都重载了两个方法：
 * 只传日志信息时默认用程序的包名作为日志的tag；
 * 如果想用类名作为tag，只需要调用重载方法并传送this即可
 * <p/>
 * Created by zk <zkzxc1988@163.com>.
 */
public class LogUtils {

    /**
     * 日志输出级别 VERBOSE
     */
    public static final int LEVEL_VERBOSE = 1;

    /**
     * 日志输出级别 DEBUG
     */
    public static final int LEVEL_DEBUG = 2;

    /**
     * 日志输出级别 INFO
     */
    public static final int LEVEL_INFO = 3;

    /**
     * 日志输出级别 WARN
     */
    public static final int LEVEL_WARN = 4;

    /**
     * 日志输出级别 ERROR
     */
    public static final int LEVEL_ERROR = 5;

    /**
     * 日志输出级别 NONE 即不输出任何日志
     */
    public static final int LEVEL_NONE = 6;

    //默认的日志输出时的tag，即程序的包名
    public static final String DEFAULT_TAG = LogUtils.class.getPackage().getName();

    //日志输出级别限制，默认为VERBOSE级别
    public static int mLogLevel = LEVEL_VERBOSE;

    /**
     * 设置日志的输出级别限制
     *
     * @param level 日志的输出级别限制
     */
    public static void setLogLevel(int level) {
        mLogLevel = level;
    }

    /**
     * 以VERBOSE级别输出日志，tag为调用该方法的类的类名
     *
     * @param o   调用该方法的对象，传this即可
     * @param msg 日志信息
     */
    public static void v(Object o, String msg) {
        if (mLogLevel <= LEVEL_VERBOSE) {
            Log.v(getTag(o), msg);
        }
    }

    /**
     * 以VERBOSE级别输出日志，tag为应用程序的包名
     *
     * @param msg 日志信息
     */
    public static void v(String msg) {
        if (mLogLevel <= LEVEL_VERBOSE) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    /**
     * 以DEBUG级别输出日志，tag为调用该方法的类的类名
     *
     * @param o   调用该方法的对象，传this即可
     * @param msg 日志信息
     */
    public static void d(Object o, String msg) {
        if (mLogLevel <= LEVEL_DEBUG) {
            Log.d(getTag(o), msg);
        }
    }

    /**
     * 以DEBUG级别输出日志，tag为应用程序的包名
     *
     * @param msg 日志信息
     */
    public static void d(String msg) {
        if (mLogLevel <= LEVEL_DEBUG) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    /**
     * 以INFO级别输出日志，tag为调用该方法的类的类名
     *
     * @param o   调用该方法的对象，传this即可
     * @param msg 日志信息
     */
    public static void i(Object o, String msg) {
        if (mLogLevel <= LEVEL_INFO) {
            Log.i(getTag(o), msg);
        }
    }

    /**
     * 以INFO级别输出日志，tag为应用程序的包名
     *
     * @param msg 日志信息
     */
    public static void i(String msg) {
        if (mLogLevel <= LEVEL_INFO) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    /**
     * 以WARN级别输出日志，tag为调用该方法的类的类名
     *
     * @param o   调用该方法的对象，传this即可
     * @param msg 日志信息
     */
    public static void w(Object o, String msg) {
        if (mLogLevel <= LEVEL_WARN) {
            Log.w(getTag(o), msg);
        }
    }

    /**
     * 以WARN级别输出日志，tag为应用程序的包名
     *
     * @param msg 日志信息
     */
    public static void w(String msg) {
        if (mLogLevel <= LEVEL_WARN) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    /**
     * 以ERROR级别输出日志，tag为调用该方法的类的类名
     *
     * @param o   调用该方法的对象，传this即可
     * @param msg 日志信息
     */
    public static void e(Object o, String msg) {
        if (mLogLevel <= LEVEL_ERROR) {
            Log.e(getTag(o), msg);
        }
    }

    /**
     * 以ERROR级别输出日志，tag为应用程序的包名
     *
     * @param msg 日志信息
     */
    public static void e(String msg) {
        if (mLogLevel <= LEVEL_ERROR) {
            Log.e(DEFAULT_TAG, msg);
        }
    }

    private static String getTag(Object o) {
        if (o != null) {
            String className = o.getClass().getName();
            return className;
        }else {
            return null;
        }
    }
}
