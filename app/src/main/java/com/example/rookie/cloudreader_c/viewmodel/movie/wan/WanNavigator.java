package com.example.rookie.cloudreader_c.viewmodel.movie.wan;

import com.example.rookie.cloudreader_c.bean.wanandroid.DuanZiBean;
import com.example.rookie.cloudreader_c.bean.wanandroid.HomeListBean;
import com.example.rookie.cloudreader_c.bean.wanandroid.WanAndroidBannerBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by rookie on 2018/4/9.
 */

public interface WanNavigator {

    interface JokeModelNavigator {
        void loadSuccess(List<DuanZiBean> lists);

        void loadFailed();

        void addSubscription(Subscription subscription);

    }

    interface JokeNavigator {

        /**
         * 加载文章列表失败
         */
        void loadListFailure();

        /**
         * 显示文章列表
         *
         * @param bean 文章数据
         */
        void showAdapterView(List<DuanZiBean> bean);

        /**
         * 刷新列表
         *
         * @param bean 文章数据
         */
        void refreshAdapter(List<DuanZiBean> bean);

        /**
         * 没有更多了
         */
        void showListNoMoreLoading();

        /**
         * 显示加载成功页面
         */
        void showLoadSuccessView();
    }

    interface WanAndroidNavigator {
        /**
         * 显示banner图
         *
         * @param bannerImages 图片链接集合
         * @param mBannerTitle 文章标题集合
         * @param result       全部数据
         */
        void showBannerView(ArrayList<String> bannerImages, ArrayList<String> mBannerTitle, List<WanAndroidBannerBean.DataBean> result);

        /**
         * 加载banner图失败
         */
        void loadBannerFailure();

        /**
         * 加载文章列表失败
         */
        void loadHomeListFailure();

        /**
         * 显示文章列表
         *
         * @param bean 文章数据
         */
        void showAdapterView(HomeListBean bean);

        /**
         * 刷新列表
         *
         * @param bean 文章数据
         */
        void refreshAdapter(HomeListBean bean);

        /**
         * 没有更多了
         */
        void showListNoMoreLoading();

        /**
         * 显示加载成功页面
         */
        void showLoadSuccessView();
    }

}
