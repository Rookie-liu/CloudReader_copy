package com.example.rookie.cloudreader_c.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.Glide;
import com.example.rookie.cloudreader_c.MainActivity;
import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.app.ConstantsImageUrl;
import com.example.rookie.cloudreader_c.databinding.ActivityTransitionBinding;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;

import java.util.Random;

public class TransitionActivity extends AppCompatActivity {


    private ActivityTransitionBinding mBinding;
    private boolean animationEnd;
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);


        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        // 随机获取一个启动图地址
        int i= new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 获取启动图, 先显示默认启动图

        //TODO error 这里报错
//        mBinding.ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));

        //TODO 这个方法是进行图片请求的嘛?Glide 常用的图片加载的三方
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(mBinding.ivPic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 延迟1500 隐藏 启动图?
                mBinding.ivDefultPic.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 延迟3500 进入主页
                toMainActivity();
            }
        }, 3500);

        // 点击跳过的方法
        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
            }
        });

    }

    // 鉴定效果跳转到主页
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void animationEnd() {
        synchronized (TransitionActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                mBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity() {
        if (isIn){
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;

    }
}
