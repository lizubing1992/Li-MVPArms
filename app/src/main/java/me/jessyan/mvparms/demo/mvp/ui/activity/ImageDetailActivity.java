package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerImageDetailComponent;
import me.jessyan.mvparms.demo.di.module.ImageDetailModule;
import me.jessyan.mvparms.demo.mvp.contract.ImageDetailContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageDetailEntity;
import me.jessyan.mvparms.demo.mvp.presenter.ImageDetailPresenter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageDetailAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.PhotoPagerAdapter;
import me.jessyan.mvparms.demo.mvp.ui.common.WEActivity;
import me.jessyan.mvparms.demo.mvp.ui.fragment.PhotoDetailFragment;
import me.jessyan.mvparms.demo.widget.EmptyLayout;
import me.jessyan.mvparms.demo.widget.PhotoViewPager;
import timber.log.Timber;

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
 * Created by xing on 2016/12/5.
 */

public class ImageDetailActivity extends WEActivity<ImageDetailPresenter> implements ImageDetailContract.View {
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
    protected void setupActivityComponent(AppComponent appComponent) {
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
        setupBackIcon(toolbar);
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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(this, intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
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