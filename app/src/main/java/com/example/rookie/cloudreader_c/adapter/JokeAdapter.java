package com.example.rookie.cloudreader_c.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewHolder;
import com.example.rookie.cloudreader_c.bean.wanandroid.DuanZiBean;
import com.example.rookie.cloudreader_c.databinding.ItemJokeBinding;
import com.example.rookie.cloudreader_c.utils.DialogBuild;
import com.example.rookie.cloudreader_c.utils.TimeUtil;

/**
 * Created by rookie on 2018/4/9.
 */

public class JokeAdapter extends BaseRecyclerViewAdapter<DuanZiBean> {

    private Activity activity;
    public  JokeAdapter (Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_joke);
    }

    private class  ViewHolder extends  BaseRecyclerViewHolder<DuanZiBean, ItemJokeBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(DuanZiBean object, int position) {
            if (object != null) {
                binding.setBean(object);
                binding.executePendingBindings();
                String time = TimeUtil.formatDataTime(Long.valueOf(object.getCreateTime() + "000"));
                binding.setTime(time);

                binding.llItemTop.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogBuild.showItems(v,object.getContent());
                        return false;
                    }
                });
            }
        }
    }

}
