package com.zk.moviememos.presenter;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface BasePresenter {

    void initOnCreate();

    void loadOnResume();

    void loadOnHiddenChanged(boolean hidden);

}
