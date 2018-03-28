package com.example.rookie.cloudreader_c.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.app.ConstantsImageUrl;
import com.example.rookie.cloudreader_c.databinding.ActivityTransitionBinding;

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

        int i= new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
//        mBinding.ivDefultPic.setImageDrawable(common);
    }
}
