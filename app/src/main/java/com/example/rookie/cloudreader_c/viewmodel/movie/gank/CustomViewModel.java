package com.example.rookie.cloudreader_c.viewmodel.movie.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.rookie.cloudreader_c.base.BaseFragment;
import com.example.rookie.cloudreader_c.bean.GankIoDataBean;
import com.example.rookie.cloudreader_c.data.remote.model.GankOtherModel;
import com.example.rookie.cloudreader_c.http.RequestImpl;

import rx.Subscription;

/**
 * Created by rookie on 2018/4/8.
 * 干货定制页面 ViewModel
 *
 */

public class CustomViewModel extends ViewModel {

    private final BaseFragment activity;
    private final GankOtherModel mModel;
    private CustomNavigator navigator;
    private int mPage = 1;
    private String mType;

    public void setNavigator(CustomNavigator navigator) {
        this.navigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public CustomViewModel(BaseFragment activity) {
        this.activity = activity;
        mModel = new GankOtherModel();
    }

    public void loadCustomData() {

        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                navigator.showLoadSuccessView();;
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null
                            && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0 ) {
                        navigator.showAdapterView(gankIoDataBean);
                    }
                }else  {
                    if (gankIoDataBean != null
                            && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0) {
                        navigator.refreshAdapter(gankIoDataBean);
                    }else  {
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






    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
