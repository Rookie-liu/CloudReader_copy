package com.example.rookie.cloudreader_c.data.remote.model;

import com.example.rookie.cloudreader_c.bean.GankIoDataBean;
import com.example.rookie.cloudreader_c.http.HttpClient;
import com.example.rookie.cloudreader_c.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rookie on 2018/4/4.
 */

public class GankOtherModel {

    private String id;
    private int page;
    private int per_page;

    public void setData(String id, int page, int per_page) {
        this.id = id;
        this.page = page;
        this.per_page = per_page;
    }

    public void getGankIoData(final RequestImpl listener) {
        Subscription subscription = HttpClient.Builder.getGankIOServer().getGankIoData(id, page, per_page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDataBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        listener.loadSuccess(gankIoDataBean);

                    }
                });
        listener.addSubscription(subscription);
    }

}
