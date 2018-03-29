package com.example.rookie.cloudreader_c.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.BaseActivity;
import com.example.rookie.cloudreader_c.databinding.ActivityNavDeedBackBinding;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;
import com.example.rookie.cloudreader_c.view.webview.WebViewActivity;

public class NavDeedBackActivity extends BaseActivity<ActivityNavDeedBackBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_deed_back);
        setTitle("问题反馈");
        showContentView();

        bindingView.tvIssues.setOnClickListener(listener);
        bindingView.tvJianshu.setOnClickListener(listener);
        bindingView.tvQq.setOnClickListener(listener);
        bindingView.tvEmail.setOnClickListener(listener);
        bindingView.tvFaq.setOnClickListener(listener);
    }


    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.tv_issues:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_issues), "Issues" );
                break;

                case R.id.tv_qq:
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2649333035";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;

                case R.id.tv_email:
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:rookie_liu@126.com"));
                    startActivity(intent);
                    break;

                case R.id.tv_jianshu:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_jianshu), "简书");
                    break;

                case R.id.tv_faq:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_faq), "常见问题归纳");
                    break;

                default:
                    break;
            }
        }
    };

    public static void start(Context mcontext) {
        Intent intent = new Intent(mcontext, NavDeedBackActivity.class);
        mcontext.startActivity(intent);
    }
}
