package com.zk.moviememos.view;

/**
 * Created by zk <zkzxc1988@163.com>.
 */
public interface BaseView<T> {

    /**
     * 将Presenter绑定给View
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示正在加载
     */
    void showProgress();

    /**
     * 隐藏正在加载
     */
    void hideProgress();

    /**
     * 当前视图是否可用
     * @return
     */
    boolean isActive();
}
