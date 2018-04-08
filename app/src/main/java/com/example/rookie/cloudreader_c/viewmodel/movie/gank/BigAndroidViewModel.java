package com.example.rookie.cloudreader_c.viewmodel.movie.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.rookie.cloudreader_c.app.CloudReaderApplication;
import com.example.rookie.cloudreader_c.base.BaseFragment;
import com.example.rookie.cloudreader_c.bean.GankIoDataBean;
import com.example.rookie.cloudreader_c.data.remote.model.GankOtherModel;
import com.example.rookie.cloudreader_c.http.RequestImpl;
import com.example.rookie.cloudreader_c.http.cache.ACache;

import rx.Subscription;

/**
 * Created by rookie on 2018/4/8.
 */

public class BigAndroidViewModel extends ViewModel {

    private final GankOtherModel mModel;
    private final ACache mACache;
    private String mType = "Android";
    private BaseFragment activity;
    private BigAndroidNavigator navigator;
    private int mPage = 1;
    public BigAndroidViewModel(BaseFragment activity, String mType) {
        this.activity = activity;
        this.mType = mType;

        mACache = ACache.get(CloudReaderApplication.getInstance());
        mModel = new GankOtherModel();
    }

    public void setBigAndroidNavigator(BigAndroidNavigator bigAndroidNavigator) {
        this.navigator = bigAndroidNavigator;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPage() {
        return mPage;
    }

    // 加载数据
    public void loadAndroidData() {
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                navigator.showLoadSuccessView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        navigator.showAdapterView(gankIoDataBean);

                    } else {
                        navigator.showLoadFailedView();
                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        navigator.refreshAdapter(gankIoDataBean);
                    } else {
                        navigator.showListNoMoreLoading();
                    }
                }
            }

            @Override
            public void loadFailed() {
                navigator.showLoadFailedView();
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                activity.addSubscription(subscription);
            }
        });
    }

    public void onDestroy() {
        navigator = null;
    }

}
