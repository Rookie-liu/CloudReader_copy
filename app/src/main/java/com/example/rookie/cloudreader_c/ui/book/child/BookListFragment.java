package com.example.rookie.cloudreader_c.ui.book.child;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.inputmethod.EditorInfo;

import com.example.rookie.cloudreader_c.MainActivity;
import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.adapter.WanAdapter;
import com.example.rookie.cloudreader_c.base.BaseFragment;
import com.example.rookie.cloudreader_c.bean.book.BookBean;
import com.example.rookie.cloudreader_c.databinding.FragmentWanAndroidBinding;
import com.example.rookie.cloudreader_c.databinding.HeaderItemBookBinding;

import com.example.rookie.cloudreader_c.http.HttpClient;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.DebugUtil;
import com.example.xrecyclerview.XRecyclerView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rookie on 2018/4/9.
 */

public class BookListFragment extends BaseFragment<FragmentWanAndroidBinding> {
    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;
    private MainActivity activity;
    private WanAdapter mBookAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    public static BookListFragment newInstance(String param1) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlBook.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlBook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlBook.postDelayed(() -> {
                    mStart = 0;
                    bindingView.xrvBook.reset();
                    loadCustomData();
                }, 1000);

            }
        });
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        bindingView.xrvBook.setLayoutManager(mLayoutManager);
        bindingView.xrvBook.setPullRefreshEnabled(false);
        bindingView.xrvBook.clearHeader();
        mBookAdapter = new WanAdapter(getActivity());
        bindingView.xrvBook.setAdapter(mBookAdapter);
        HeaderItemBookBinding oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_book, null, false);
        bindingView.xrvBook.addHeaderView(oneBinding.getRoot());
        oneBinding.etTypeName.setText(mType);
        oneBinding.etTypeName.setSelection(mType.length());
        /** 处理键盘搜索键 */
        oneBinding.etTypeName.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mStart = 0;
                bindingView.xrvBook.reset();
                mType = oneBinding.etTypeName.getText().toString().trim();
                loadCustomData();
            }
            return false;
        });
        bindingView.xrvBook.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mStart += mCount;
                loadCustomData();
            }
        });
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlBook.setRefreshing(true);
        bindingView.srlBook.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCustomData();
            }
        }, 500);
        DebugUtil.error("-----setRefreshing");
    }

    private void loadCustomData() {

        Subscription get = HttpClient.Builder.getDouBanService().getBook(mType, mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                        if (bindingView.srlBook.isRefreshing()) {
                            bindingView.srlBook.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showContentView();
                        if (bindingView.srlBook.isRefreshing()) {
                            bindingView.srlBook.setRefreshing(false);
                        }
                        if (mStart == 0) {
                            showError();
                        } else {
                            bindingView.xrvBook.refreshComplete();
                        }
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        if (mStart == 0) {
                            if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {

                                mBookAdapter.clear();
                                mBookAdapter.addAll(bookBean.getBooks());
                                mBookAdapter.notifyDataSetChanged();
                                bindingView.xrvBook.refreshComplete();
                            }
                            mIsFirst = false;
                        } else {
                            mBookAdapter.addAll(bookBean.getBooks());
                            mBookAdapter.notifyDataSetChanged();
                            bindingView.xrvBook.refreshComplete();
                        }
                    }
                });
        addSubscription(get);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
