package com.example.rookie.cloudreader_c.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewHolder;
import com.example.rookie.cloudreader_c.bean.book.BooksBean;
import com.example.rookie.cloudreader_c.databinding.ItemBookBinding;
import com.example.rookie.cloudreader_c.ui.book.child.BookDetailActivity;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;

/**
 * Created by rookie on 2018/4/9.
 */

public class WanAdapter extends BaseRecyclerViewAdapter<BooksBean> {

    private Activity activity;

    public WanAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_book);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<BooksBean, ItemBookBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final BooksBean book, final int position) {
            if (book != null) {
                binding.setBean(book);
                binding.executePendingBindings();

                binding.llItemTop.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        BookDetailActivity.start(activity,book,binding.ivTopPhoto);
                    }
                });
            }
        }
    }
}
