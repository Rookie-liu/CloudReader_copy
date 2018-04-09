package com.example.rookie.cloudreader_c.ui.book.child;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.example.rookie.cloudreader_c.R;
import com.example.rookie.cloudreader_c.base.BaseHeaderActivity;
import com.example.rookie.cloudreader_c.bean.book.BookDetailBean;
import com.example.rookie.cloudreader_c.bean.book.BooksBean;
import com.example.rookie.cloudreader_c.databinding.ActivityBookDetailBinding;
import com.example.rookie.cloudreader_c.databinding.HeaderBookDetailBinding;
import com.example.rookie.cloudreader_c.http.HttpClient;
import com.example.rookie.cloudreader_c.utils.CommonUtils;
import com.example.rookie.cloudreader_c.utils.DebugUtil;
import com.example.rookie.cloudreader_c.view.webview.WebViewActivity;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookDetailActivity extends BaseHeaderActivity<HeaderBookDetailBinding, ActivityBookDetailBinding> {


    private BooksBean booksBean;
    private String mBookDetailUrl;
    private String mBookDetailName;
    public final static String EXTRA_PARAM = "bookBean";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (getIntent() != null) {
            booksBean = (BooksBean) getIntent().getSerializableExtra(EXTRA_PARAM);
        }

        setMotion(setHeaderPicView(),true);
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

        setTitle(booksBean.getTitle());
        setSubTitle("作者：" + booksBean.getAuthor());
        bindingHeaderView.setBooksBean(booksBean);
        bindingHeaderView.executePendingBindings();

        loadBookDetail();
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_book_detail;
    }

    private void loadBookDetail() {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getDouBanService().getBookDetail(booksBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(final BookDetailBean bookDetailBean) {

                        mBookDetailUrl = bookDetailBean.getAlt();
                        mBookDetailName = bookDetailBean.getTitle();
                        bindingContentView.setBookDetailBean(bookDetailBean);
                        bindingContentView.executePendingBindings();
                    }
                });
        addSubscription(get);
    }

    @Override
    protected void setTitleClickMore() {
        WebViewActivity.loadUrl(this, mBookDetailUrl, mBookDetailName);
    }

    @Override
    protected String setHeaderImgUrl() {
        if (booksBean == null) {
            return "";
        }
        return booksBean.getImages().getMedium();
    }

    @Override
    protected ImageView setHeaderImageView() {
        return bindingHeaderView.imgItemBg;
    }

    @Override
    protected ImageView setHeaderPicView() {
        return bindingHeaderView.ivOnePhoto;
    }

    @Override
    protected void onRefresh() {
        loadBookDetail();
    }

    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, BooksBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_PARAM, positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_book_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
