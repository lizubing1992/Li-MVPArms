package me.jessyan.mvparms.demo.mvp.ui.activity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jess.arms.di.component.AppComponent;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.base.BaseRefreshActivity;
import me.jessyan.mvparms.demo.di.component.DaggerImageDetailComponent;
import me.jessyan.mvparms.demo.di.module.ImageDetailModule;
import me.jessyan.mvparms.demo.mvp.contract.ImageDetailContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageDetailEntity;
import me.jessyan.mvparms.demo.mvp.presenter.ImageDetailPresenter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageDetailAdapter;
import me.jessyan.mvparms.demo.widget.EmptyLayout;
import me.jessyan.mvparms.demo.widget.PhotoViewPager;
import timber.log.Timber;


public class ImageDetailActivity extends BaseRefreshActivity<ImageDetailPresenter> implements ImageDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    PhotoViewPager viewpager;

    @BindView(R.id.photo_detail_title_tv)
    TextView photoDetailTitleTv;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;
    private ImageDetailEntity entity;
    private int id;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerImageDetailComponent
                .builder()
                .appComponent(appComponent)
                .imageDetailModule(new ImageDetailModule(this)) //请将ImageDetailModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    protected void loadData() {
        id = getIntent().getIntExtra("imageDetailId", 1);
        setupBackIcon();
        mPresenter.requestImageDetail(id);
    }



    @Override
    public void showLoading() {
        emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    }

    @Override
    public void hideLoading() {
        emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        WEApplication.showToast(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void ComponentInject() {

    }

    @Override
    public void setImageDetail(ImageDetailEntity entity) {
        if (entity != null) {
            Timber.tag(TAG).w("ImageDetailEntity" + entity.getList().size());
            this.entity = entity;
            initViewPager();
            setPhotoDetailTitle(0);
        }
    }

    private void initViewPager() {
        ImageDetailAdapter imageDetailAdapter = new ImageDetailAdapter(entity.getList());
        viewpager.setAdapter(imageDetailAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoDetailTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPhotoDetailTitle(int position) {
        String title = entity.getTitle();
        photoDetailTitleTv.setText(getString(R.string.photo_detail_title, position + 1,
                entity.getList().size(), title));
    }
}