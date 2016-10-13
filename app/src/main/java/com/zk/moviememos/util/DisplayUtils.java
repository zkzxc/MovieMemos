package com.zk.moviememos.util;

/**
 * Created by zk <zkzxc1988@163.com>.
 */

import android.content.Context;
import android.text.TextPaint;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.11.12
 */
public class DisplayUtils {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将sp值转换为dp值
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2dp(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度,单位是px
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度,单位是px
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取TextView里字符串的宽度，单位是px
     * @param textView
     * @param text
     * @return
     */
    public static float getTextViewWidth(TextView textView, String text) {
        TextPaint textPaint = textView.getPaint();
        return textPaint.measureText(text);
    }

}