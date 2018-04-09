package com.example.rookie.cloudreader_c.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewHolder;
import com.example.rookie.cloudreader_c.bean.wanandroid.HomeListBean;
import com.example.rookie.cloudreader_c.databinding.ItemWanAndroidBinding;
import com.example.rookie.cloudreader_c.view.webview.WebViewActivity;

/**
 * Created by rookie on 2018/4/9.
 */

public class WanAndroidAdapter extends BaseRecyclerViewAdapter<HomeListBean.DataBean.DatasBean> {

    private Activity activity;

    public WanAndroidAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_wan_android);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<HomeListBean.DataBean.DatasBean, ItemWanAndroidBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final HomeListBean.DataBean.DatasBean bean, final int position) {
            if (bean != null) {
                binding.setBean(bean);
                binding.setAdapter(WanAndroidAdapter.this);
                binding.executePendingBindings();

//                binding.llItemTop.setOnClickListener(new PerfectClickListener() {
//                    @Override
//                    protected void onNoDoubleClick(View v) {
//                        WebViewActivity.loadUrl(v.getContext(),bean.getLink(),bean.getTitle());
//                    }
//                });
            }
        }
    }

    public void openDetail(HomeListBean.DataBean.DatasBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }
}
