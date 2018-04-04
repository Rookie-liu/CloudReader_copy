package com.example.rookie.cloudreader_c.viewmodel.movie.gank;

import com.example.rookie.cloudreader_c.bean.GankIoDataBean;

/**
 * Created by rookie on 2018/4/4.
 */

public interface BigAndroidNavigator {

    void showLoadSuccessView();

    /**
     * 显示adapter数据
     */
    void showAdapterView(GankIoDataBean gankIoDataBean);

    /**
     * 刷新adapter数据
     */
    void refreshAdapter(GankIoDataBean gankIoDataBean);

    /**
     * 显示列表没有更多数据布局
     */
    void showListNoMoreLoading();

    void showLoadFailedView();
}

