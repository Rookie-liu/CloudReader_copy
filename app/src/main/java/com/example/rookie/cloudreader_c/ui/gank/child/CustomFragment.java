package com.example.rookie.cloudreader_c.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.adapter.AndroidAdapter;
import com.example.rookie.cloudreader_c.base.BaseFragment;
import com.example.rookie.cloudreader_c.bean.GankIoDataBean;
import com.example.rookie.cloudreader_c.databinding.FragmentCustomBinding;
import com.example.rookie.cloudreader_c.utils.SPUtils;
import com.example.rookie.cloudreader_c.utils.ToastUtil;
import com.example.rookie.cloudreader_c.viewmodel.movie.gank.CustomNavigator;
import com.example.rookie.cloudreader_c.viewmodel.movie.gank.CustomViewModel;
import com.example.xrecyclerview.XRecyclerView;

import static com.example.rookie.cloudreader_c.app.Constants.GANK_CALA;

/**
 * Created by rookie on 2018/4/8.
 * 定制干货
 */

public class CustomFragment extends BaseFragment<FragmentCustomBinding> implements CustomNavigator {

    private static final String TAG = "CustomFragment";
    private String mType = "all";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private AndroidAdapter mAndroidAdapter;
    private View mHeaderView;
    private CustomViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new CustomViewModel(this);
        viewModel.setNavigator(this);

        initData();
        initRecyclerView();
        //准备就绪
        mIsPrepared = true;

    }

    private void  initData() {
        String type = SPUtils.getString(GANK_CALA,"全部");
        if ("全部".equals(type)) {
            mType = "all";
        }else if ("IOS".equals(type)) {
            mType = "iOS";
        }else  {
            mType = type;
        }
        viewModel.setType(mType);
    }

    private void  initRecyclerView() {
        //禁止下拉刷新
        bindingView.xrvCustom.setPullRefreshEnabled(false);
        //去掉舒心投
        bindingView.xrvCustom.clearHeader();
        mAndroidAdapter = new AndroidAdapter(getActivity());
        bindingView.xrvCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                viewModel.loadCustomData();
            }
        });
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        viewModel.loadCustomData();
    }

    /**
     * 设置adapter
     */

    private void setAdapter(GankIoDataBean mCustomBean) {
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
            bindingView.xrvCustom.addHeaderView(mHeaderView);
        }
        initHeader(mHeaderView);
        boolean isAll = SPUtils.getString(GANK_CALA, "全部").equals("全部");

        mAndroidAdapter.clear();
        mAndroidAdapter.setAllType(isAll);
        mAndroidAdapter.addAll(mCustomBean.getResults());
        bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvCustom.setAdapter(mAndroidAdapter);
        mAndroidAdapter.notifyDataSetChanged();
        mIsFirst = false;

    }

    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String ganCala = SPUtils.getString(GANK_CALA, "全部");
        txName.setText(ganCala);
        View view = mHeaderView.findViewById(R.id.ll_chooset_catalogue);
        view.setOnClickListener(v -> new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
        .title("选择分类")
        .sheet(R.menu.gank_bottomsheet).listener((dialog, which) -> {

            switch (which) {

                case R.id.gank_all:
                    if (isOtherType("全部")){
                        txName.setText("全部");
                        mType = "all";
                        viewModel.setType(mType);
                        viewModel.setPage(1);
                        mAndroidAdapter.clear();
                        SPUtils.putString(GANK_CALA, "全部");
                        showLoading();
                        viewModel.loadCustomData();
                    }
                    break;
                case R.id.gank_ios:
                    if (isOtherType("IOS")) {
                        txName.setText("IOS");
                        mType = "iOS";
                        viewModel.setType(mType);
                        viewModel.setPage(1);
                        mAndroidAdapter.clear();
                        SPUtils.putString(GANK_CALA, "IOS");
                        showLoading();
                        viewModel.loadCustomData();
                    }
                    break;
                case R.id.gank_qian:
                    if (isOtherType("前端")) {
                        changeContent(txName, "前段");
                    }
                    break;
                case R.id.gank_app:
                    if (isOtherType("App")) {
                        changeContent(txName, "App");
                    }
                    break;
                case  R.id.gank_movie:
                    if (isOtherType("休息视频")) {
                        changeContent(txName, "休息视频");
                    }
                    break;
                case R.id.gank_resouce:
                    if (isOtherType("拓展资源")) {
                        changeContent(txName, "拓展资源");
                    }
                    break;

            }

                }).show());

    }


    private void changeContent(TextView textView, String content) {
        textView.setText(content);
        mType = content;
        viewModel.setType(mType);
        viewModel.setPage(1);
        mAndroidAdapter.clear();
        SPUtils.putString(GANK_CALA, content);
        showLoading();
        viewModel.loadCustomData();

    }
    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString(GANK_CALA, "全部");
        if (clickText.equals(selectType)) {
            ToastUtil.showToast("当前已经是" + selectType + "分类");
            return false;
        }else  {
            bindingView.xrvCustom.reset();
            return true;
        }
    }


    @Override
    protected void onRefresh() {
        viewModel.loadCustomData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoadSuccessView() {
        showContentView();
    }

    @Override
    public void showAdapterView(GankIoDataBean gankIoDataBean) {
        setAdapter(gankIoDataBean);
    }

    @Override
    public void refreshAdapter(GankIoDataBean gankIoDataBean) {
        mAndroidAdapter.addAll(gankIoDataBean.getResults());
        bindingView.xrvCustom.refreshComplete();
        mAndroidAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListNoMoreLoading() {
        bindingView.xrvCustom.noMoreLoading();
    }

    @Override
    public void showLoadFailedView() {
        bindingView.xrvCustom.refreshComplete();
        if (mAndroidAdapter.getItemCount() == 0) {
            showError();
        }
    }
}
