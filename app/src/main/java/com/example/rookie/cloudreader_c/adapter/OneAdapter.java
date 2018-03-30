package com.example.rookie.cloudreader_c.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.rookie.cloudreader_c.base.baseadapter.BaseRecyclerViewHolder;
import com.example.rookie.cloudreader_c.bean.moviechild.SubjectsBean;
import com.example.rookie.cloudreader_c.databinding.ItemOneBinding;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by rookie on 2018/3/30.
 */

public class OneAdapter extends BaseRecyclerViewAdapter<SubjectsBean> {


    private Activity activity;

    public OneAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_one);
    }

    private class  ViewHolder extends  BaseRecyclerViewHolder<SubjectsBean, ItemOneBinding> {
        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }


        @Override
        public void onBindViewHolder(final SubjectsBean positionData, final int position) {

            if (positionData != null){
                binding.setSubjectsBean(positionData);
                binding.setCallback(subjectsBean -> com.example.rookie.cloudreader_c.ui.one.OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto));
                binding.executePendingBindings();

                ViewHelper.setScaleX(itemView,0.8f);
                ViewHelper.setScaleY(itemView,0.8f);
                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();


            }

        }
    }
}
