package com.example.rookie.cloudreader_c.data.remote.model;

import android.text.TextUtils;

import com.example.rookie.cloudreader_c.bean.wanandroid.DuanZiBean;
import com.example.rookie.cloudreader_c.bean.wanandroid.NhdzListBean;
import com.example.rookie.cloudreader_c.bean.wanandroid.QsbkListBean;
import com.example.rookie.cloudreader_c.http.HttpClient;
import com.example.rookie.cloudreader_c.viewmodel.movie.wan.WanNavigator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by rookie on 2018/4/9.
 */

public class JokeModel {


    /**
     * 内涵段子请求
     * @param listener
     * @param page
     */
    public void showNhdzList(final WanNavigator.JokeModelNavigator listener, int page) {
        Func1<NhdzListBean, Observable<List<DuanZiBean>>> func1 = new Func1<NhdzListBean, Observable<List<DuanZiBean>>>() {
            @Override
            public Observable<List<DuanZiBean>> call(NhdzListBean bean) {
                List<DuanZiBean> lists = new ArrayList<>();

                if (bean != null && bean.getData() != null && bean.getData().getData() != null
                        && bean.getData().getData().size() > 0) {
                    List<NhdzListBean.DataBeanX.DataBean> data = bean.getData().getData();
                    for (NhdzListBean.DataBeanX.DataBean bean1 : data) {
                        NhdzListBean.DataBeanX.DataBean.GroupBean group = bean1.getGroup();
                        if (group != null) {
                            DuanZiBean duanZiBean = new DuanZiBean();
                            duanZiBean.setCategoryName(group.getCategory_name());
                            duanZiBean.setContent(group.getContent());
                            duanZiBean.setCreateTime(group.getCreate_time());
                            NhdzListBean.DataBeanX.DataBean.GroupBean.UserBean user = group.getUser();
                            if (user != null) {
                                duanZiBean.setAvatarUrl(user.getAvatar_url());
                                duanZiBean.setName(user.getName());
                            }
                            lists.add(duanZiBean);
                        }
                    }
                }
                return Observable.just(lists);
            }
        };

        Observer<List<DuanZiBean>> observer = new Observer<List<DuanZiBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.loadFailed();
            }

            @Override
            public void onNext(List<DuanZiBean> lists) {
                listener.loadSuccess(lists);
            }
        };

        Subscription subscription = HttpClient.Builder.getNHDZServer().getNhdzList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(func1)
                .subscribe(observer);
        listener.addSubscription(subscription);
    }


    /**
     * 糗事百科列表
     * @param listener
     * @param page
     */
    public void showQSBKList(final WanNavigator.JokeModelNavigator listener, int page) {
        Func1<QsbkListBean, Observable<List<DuanZiBean>>> func1 = new Func1<QsbkListBean, Observable<List<DuanZiBean>>>() {
            @Override
            public Observable<List<DuanZiBean>> call(QsbkListBean bean) {

                List<DuanZiBean> lists = new ArrayList<>();
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    List<QsbkListBean.ItemsBean> items = bean.getItems();
                    for (QsbkListBean.ItemsBean bean1 : items) {
                        DuanZiBean duanZiBean = new DuanZiBean();
                        duanZiBean.setContent(bean1.getContent());
                        duanZiBean.setCreateTime(bean1.getPublished_at());
                        QsbkListBean.ItemsBean.TopicBean topic = bean1.getTopic();
                        QsbkListBean.ItemsBean.UserBean user = bean1.getUser();
                        if (topic != null) {
                            duanZiBean.setCategoryName(topic.getContent());
                        }
                        if (user != null) {
                            duanZiBean.setName(user.getLogin());
                            String thumb = user.getThumb();
                            if (!TextUtils.isEmpty(thumb)) {
                                StringBuffer stringBuffer = new StringBuffer();
//                                String s = thumb.replaceAll("\\\\\\\\", "\\");
                                stringBuffer.append("http:");
                                stringBuffer.append(thumb);
                                duanZiBean.setAvatarUrl(stringBuffer.toString());
                            }
                        }
//                        bean1.getHot_comment();
                        lists.add(duanZiBean);
                    }
                }

                return Observable.just(lists);
            }
        };

        Observer<List<DuanZiBean>> observer = new Observer<List<DuanZiBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.loadFailed();
            }

            @Override
            public void onNext(List<DuanZiBean> lists) {
                listener.loadSuccess(lists);
            }
        };

        Subscription subscription = HttpClient.Builder.getQSBKServer().getQsbkList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(func1)
                .subscribe(observer);
        listener.addSubscription(subscription);
    }

}
