package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jess.arms.utils.UiUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.FrescoHelper;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerNewsDetailComponent;
import me.jessyan.mvparms.demo.di.module.NewsDetailModule;
import me.jessyan.mvparms.demo.mvp.contract.NewsDetailContract;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsDetailEntity;
import me.jessyan.mvparms.demo.mvp.presenter.NewsDetailPresenter;
import me.jessyan.mvparms.demo.mvp.ui.common.WEActivity;
import me.jessyan.mvparms.demo.utils.MyUtils;
import me.jessyan.mvparms.demo.utils.TransformUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by xing on 2016/12/8.
 */

public class NewsDetailActivity extends WEActivity<NewsDetailPresenter> implements NewsDetailContract.View {


    @BindView(R.id.news_detail_photo_iv)
    SimpleDraweeView newsDetailPhotoIv;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private String mNewsTitle;
    protected Subscription mSubscription;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsDetailComponent
                .builder()
                .appComponent(appComponent)
                .newsDetailModule(new NewsDetailModule(this)) //请将NewsDetailModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_new_detail, null, false);
    }

    @Override
    protected void initData() {
        int newsId = getIntent().getIntExtra("newsId", 1);
        setupBackIcon(toolbar);
        mPresenter.getNewsDetail(newsId);
    }


    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(this, intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void loadData(NewsDetailEntity newsDetail) {
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getFromname();
        String newsTime = MyUtils.TimeStamp2Date(newsDetail.getTime() + "");
        String newsBody = newsDetail.getMessage();


        setToolBarLayout(mNewsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        FrescoHelper.displayImageview(newsDetailPhotoIv,"http://tnfs.tngou.net/img"+newsDetail.getImg(),false,1);
        setNewsDetailBodyTv(newsDetail, newsBody);
    }

    private void setNewsDetailBodyTv(final NewsDetailEntity newsDetail, final String newsBody) {
        mSubscription = Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(TransformUtils.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                        mFab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
                    }
                });
    }


    private void setToolBarLayout(String newsTitle) {
        mToolbarLayout.setTitle(newsTitle);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtils.cancelSubscription(mSubscription);
    }
}