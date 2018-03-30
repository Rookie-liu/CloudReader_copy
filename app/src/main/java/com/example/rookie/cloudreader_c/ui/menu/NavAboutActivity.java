package com.example.rookie.cloudreader_c.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.BaseActivity;
import com.example.rookie.cloudreader_c.databinding.ActivityNavAboutBinding;
import com.example.rookie.cloudreader_c.utils.BaseTools;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;
import com.example.rookie.cloudreader_c.utils.UpdateUtil;
import com.example.rookie.cloudreader_c.view.webview.WebViewActivity;

/**
 * The type Nav about activity.
 */
public class NavAboutActivity extends BaseActivity<ActivityNavAboutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showContentView();
        setTitle("关于云阅-C");
        bindingView.tvVersionName.setText("当前版本 V" + BaseTools.getVersionName());

        //TODO  这里下划线 , 抗锯齿是啥
        Glide.with(this).load(R.drawable.ic_cloudreader).into(bindingView.ivIcon);
        bindingView.tvGankio.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        bindingView.tvGankio.getPaint().setAntiAlias(true);
        bindingView.tvDouban.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        bindingView.tvDouban.getPaint().setAntiAlias(true);

        initListener();

    }



    private void initListener() {
        bindingView.tvGankio.setOnClickListener(listener);
        bindingView.tvDouban.setOnClickListener(listener);
        bindingView.tvAboutStar.setOnClickListener(listener);
        bindingView.tvFunction.setOnClickListener(listener);

    }

    //TODO 为啥有些用click 有些用这个来监听呢
    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {

            String url = null;
            String title = "加载中";
            switch (v.getId()) {
                case R.id.tv_gankio:
                    url = CommonUtils.getString(R.string.string_url_gankio);
                    title = "干货集中营";
                    break;
                case R.id.tv_douban:
                    url = CommonUtils.getString(R.string.string_url_douban);
                    title = "豆瓣开发者服务使用条款";
                    break;
                case R.id.tv_about_star:
                    url = CommonUtils.getString(R.string.string_url_cloudreader);
                    title = "CloudReader";
                    break;
                case R.id.tv_function:// 更新日志
                    url = CommonUtils.getString(R.string.string_url_update_log);
                    title = "更新日志";
                    break;
                case R.id.tv_new_version:// 检查更新
                    url = CommonUtils.getString(R.string.string_url_new_version);
                    title = "检查更新";
                    break;
                default:
                    break;
            }
            WebViewActivity.loadUrl(v.getContext(), url, title);
        }
    };

    public static  void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAboutActivity.class);
        mContext.startActivity(intent);
    }

    public void  checkUpdate(View view){
        UpdateUtil.check(this, true);
    }
}
