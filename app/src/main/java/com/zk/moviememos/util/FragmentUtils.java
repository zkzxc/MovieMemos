package com.zk.moviememos.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zk.moviememos.R;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public class FragmentUtils {

    public static void addfragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();

    }

    public static void addfragment(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    /**
     * 切换Fragment，即hide一个，show另一个
     *
     * @param fragmentManager
     * @param from
     * @param to
     * @param addToBackStack  此次切换是否加入backStack
     */
    public static void switchFragment(FragmentManager fragmentManager, Fragment from, Fragment to, boolean
            addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            fragmentTransaction.hide(from).add(from.getId(), to);
        } else {
            fragmentTransaction.hide(from).show(to);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(to.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    public static void switchFragment(FragmentManager fragmentManager, Fragment from, Fragment to, String tag,
                                      boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, android.R.anim.slide_out_right,
                R.anim.slide_in_right, android.R.anim.slide_out_right);
        if (!to.isAdded()) {
            fragmentTransaction.hide(from).add(from.getId(), to, tag);
        } else {
            fragmentTransaction.hide(from).show(to);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(to.getClass().getName());
        }
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }
}
