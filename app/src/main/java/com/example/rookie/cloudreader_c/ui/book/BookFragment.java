package com.example.rookie.cloudreader_c.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.BaseFragment;
import com.example.rookie.cloudreader_c.databinding.FragmentBookBinding;
import com.example.rookie.cloudreader_c.ui.book.child.JokeFragment;
import com.example.rookie.cloudreader_c.ui.book.child.WanAndroidFragment;
import com.example.rookie.cloudreader_c.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/4/9.
 */

public class BookFragment extends BaseFragment<FragmentBookBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragemnts = new ArrayList<>(3);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据 设置数据内容
         */

        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragemnts, mTitleList);
        bindingView.vpBook.setAdapter(myAdapter);
        bindingView.vpBook.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);
        showContentView();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_book;
    }

    private void initFragmentList() {
        mTitleList.add("玩安卓");
        mTitleList.add("书籍");
        mTitleList.add("段子");
        mFragemnts.add(WanAndroidFragment.newInstance("玩安卓"));
        mFragemnts.add(JokeFragment.newInstance("段子"));
        mFragemnts.add(JokeFragment.newInstance("段子"));

    }
}
