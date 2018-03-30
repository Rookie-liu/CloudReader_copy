package com.example.rookie.cloudreader_c.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewHolder;
import com.example.rookie.cloudreader_c.bean.moviechild.PersonBean;
import com.example.rookie.cloudreader_c.databinding.ItemMovieDetailPersonBinding;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;
import com.example.rookie.cloudreader_c.view.webview.WebViewActivity;


/**
 * Created by jingbin on 2016/12/10.
 */

public class MovieDetailAdapter extends BaseRecyclerViewAdapter<PersonBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_movie_detail_person);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<PersonBean, ItemMovieDetailPersonBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final PersonBean bean, int position) {
            binding.setPersonBean(bean);
            binding.llItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (bean != null && !TextUtils.isEmpty(bean.getAlt())) {
                        WebViewActivity.loadUrl(v.getContext(), bean.getAlt(), bean.getName());
                    }
                }
            });
        }
    }
}
