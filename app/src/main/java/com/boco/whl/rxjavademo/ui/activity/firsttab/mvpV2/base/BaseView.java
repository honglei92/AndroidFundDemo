package com.boco.whl.rxjavademo.ui.activity.firsttab.mvpV2.base;

/**
 * <pre>
 *  author : honglei92
 *  desc :
 *  blog :
 *  createtime : 2017/5/22 0022 10:42
 *  updatetime : 2017/5/22 0022 10:42
 * </pre>
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
