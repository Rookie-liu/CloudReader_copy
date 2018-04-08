package com.example.rookie.cloudreader_c.viewmodel.movie.gank;

import com.example.rookie.cloudreader_c.bean.AndroidBean;
import com.example.rookie.cloudreader_c.bean.FrontpageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2018/4/8.
 */

public interface EverydayNavigator {

    /**
     * 显示列表数据
     */

    void showListView(ArrayList<List<AndroidBean>> mLists);

    /**
     * 错误页面
     */
    void  showErrorView();

    /**
     * 轮播图
     */
    void showBannerView(ArrayList<String> mBannerImages, List<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result);

    /**
     *旋转动画
     */
    void showRotaLoading();

    /**
     * 有一个变量需要单独设置
     */
    void setIsOldDayRequest(boolean isOldDayRequest);

    /**
     * 取缓存
     */
    void getACacheData();
}
