package com.zk.moviememos.util;

import android.util.Log;

import com.zk.moviememos.po.DoubanCelebrity;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class ListUtils {

    /**
     * 将String数组转换为"str1/str2/str3"的形式
     *
     * @param list
     * @return
     */
    public static String stringListToString(List<String> list) {
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next()).append("/");
            }
            return sb.substring(0, sb.length() - 1);
        } else {
            Log.i("ListUtils", "the list is null or has no element.");
            return null;
        }
    }

    /**
     * 将DoubanCelebrity数组转换为"name1/name2/name3"的形式
     *
     * @param list
     * @return
     */
    public static String doubanCelebrityListToString(List<DoubanCelebrity> list) {
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<DoubanCelebrity> iterator = list.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next().getName()).append("/");
            }
            return sb.substring(0, sb.length() - 1);
        } else {
            Log.i("ListUtils", "the list is null or has no element.");
            return null;
        }
    }
}
