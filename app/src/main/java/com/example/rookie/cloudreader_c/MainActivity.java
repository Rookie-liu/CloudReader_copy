package com.example.rookie.cloudreader_c;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rookie.cloudreader_c.app.ConstantsImageUrl;
import com.example.rookie.cloudreader_c.databinding.ActivityMainBinding;
import com.example.rookie.cloudreader_c.databinding.NavHeaderMainBinding;
import com.example.rookie.cloudreader_c.http.rx.RxBus;
import com.example.rookie.cloudreader_c.http.rx.RxBusBaseMessage;
import com.example.rookie.cloudreader_c.http.rx.RxCodeConstants;
import com.example.rookie.cloudreader_c.ui.menu.NavDeedBackActivity;
import com.example.rookie.cloudreader_c.ui.menu.NavDownloadActivity;
import com.example.rookie.cloudreader_c.ui.menu.NavHomePageActivity;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.ImgLoadUtil;
import com.example.rookie.cloudreader_c.utils.PerfectClickListener;
import com.example.rookie.cloudreader_c.utils.SPUtils;
import com.example.rookie.cloudreader_c.view.MyFragmentPagerAdapter;
import com.example.rookie.cloudreader_c.view.statusbar.StatusBarUtil;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FrameLayout llTitleMenu;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ViewPager vpContent;

    // 对应的bean
    private ActivityMainBinding mBinding;
    private ImageView llTitleGank;
    private ImageView llTitleOne;
    private ImageView llTitleDou;
    private CompositeSubscription mCompositeSubscription;
    private NavHeaderMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initStatusView();
        initId();
        initRxBus();

        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout, CommonUtils.getColor(R.color.colorTheme));
//        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout, 123);
        initContentFragment();
        initDrawerLayout();
        initListener();

    }

    // 初始化view
    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        mBinding.include.viewStatus.setLayoutParams(layoutParams);
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navView = mBinding.navView;
        fab = mBinding.include.fab;
        toolbar = mBinding.include.toolbar;
        llTitleMenu = mBinding.include.llTitleMenu;
        vpContent = mBinding.include.vpContent;
        fab.setVisibility(View.GONE);

        llTitleGank = mBinding.include.ivTitleGank;
        llTitleOne = mBinding.include.ivTitleOne;
        llTitleDou = mBinding.include.ivTitleDou;
    }

    // 初始化抽屉的一些内容
    private void initDrawerLayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
        bind = DataBindingUtil.bind(headerView);
        bind.setListener(this);
                bind.dayNightSwitch.setChecked(SPUtils.getNightMode());

        ImgLoadUtil.displayCircle(bind.ivAvatar, ConstantsImageUrl.IC_AVATAR);
        bind.llNavExit.setOnClickListener(this);
        bind.ivAvatar.setOnClickListener(this);

        bind.llNavHomepage.setOnClickListener(listener);
        bind.llNavScanDownload.setOnClickListener(listener);
        bind.llNavDeedback.setOnClickListener(listener);
        bind.llNavAbout.setOnClickListener(listener);
        bind.llNavLogin.setOnClickListener(listener);
    }

    // 绑定点击方法
    private void initListener() {
        llTitleMenu.setOnClickListener(this);
        mBinding.include.ivTitleGank.setOnClickListener(this);
        mBinding.include.ivTitleDou.setOnClickListener(this);
        mBinding.include.ivTitleOne.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    // 初始化加载三个页面. 放到一个 adapter里面
    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
//        mFragmentList.add(new OneFragment());
//        mFragmentList.add(new GankFragment());
//        mFragmentList.add(new BookFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);
        mBinding.include.ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        // TODO 这里解开注释提示不兼容类型
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            MainActivity.setDisplayShowTitleEnabled(false);
//        }
        llTitleGank.setSelected(true);
        llTitleDou.setSelected(true);
        llTitleOne.setSelected(true);
        vpContent.setCurrentItem(1);
    }

    // 抽屉里面的点击方法
    private PerfectClickListener listener = (new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            mBinding.drawerLayout.postDelayed(() -> {
                switch (v.getId()) {
                    case R.id.ll_nav_homepage:
                        NavHomePageActivity.startHome(MainActivity.this); //主页
                        break;
                    case R.id.ll_nav_scan_download:
                        NavDownloadActivity.start(MainActivity.this); // 扫码下载
                        break;
                    case R.id.ll_nav_deedback:
                        NavDeedBackActivity.start(MainActivity.this);
                        break;
                    case R.id.ll_nav_about:
                        break;
                    case R.id.ll_nav_login:
                        break;
                    default:
                        break;
                }


            }, 260);
        }
    });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu: // 开启菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank: //干货
                break;
            case R.id.iv_title_one: // 电影
                break;
            case R.id.iv_title_dou: // 书籍
                break;
            case R.id.iv_avatar: // 进入到github
                break;
            case R.id.ll_nav_exit:
                finish();
                break;
            default:
                break;

        }
    }

    //夜间模式 , 暂时还没有
    public boolean getNightMode() {
        return SPUtils.getNightMode();
    }
    public void onNightModeClick(View view) {
        if (!SPUtils.getNightMode()) {
//            SkinCompatManager.getInstance().loadSkin(Constants.NIGHT_SKIN);
        } else {
            // 恢复应用默认皮肤
//            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
        SPUtils.setNightMode(!SPUtils.getNightMode());
        bind.dayNightSwitch.setChecked(SPUtils.getNightMode());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                llTitleGank.setSelected(false);
                llTitleOne.setSelected(true);
                llTitleDou.setSelected(false);
                break;
            case 1:
                llTitleOne.setSelected(false);
                llTitleGank.setSelected(true);
                llTitleDou.setSelected(false);
                break;
            case 2:
                llTitleDou.setSelected(true);
                llTitleOne.setSelected(false);
                llTitleGank.setSelected(false);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else  {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            }else  {
                moveTaskToBack(true);
            }
            return true;
        }
        return  super.onKeyDown(keyCode, event);
    }

    private void initRxBus() {
        Subscription subscribe = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .subscribe(integer -> mBinding.include.vpContent.setCurrentItem(0));
        addSubscription(subscribe);

    }
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
