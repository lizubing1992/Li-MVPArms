package me.jessyan.mvparms.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.DeviceUtils;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import javax.inject.Inject;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.widget.LoadProgress;

public abstract class BaseRefreshActivity<P extends IPresenter> extends BaseActivity<P> {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    protected TextView toolbarTitleTV; //标题f
    protected Toolbar toolbar;
    protected PtrClassicFrameLayout ptr;
    protected Context mContext;
    protected LoadProgress mLoadProgress = null;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * inflate布局
     */
    protected View inflateView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        ComponentInject();//依赖注入
        initView(savedInstanceState);//初始化页面控件
        setupBackIcon();//设置返回按钮
        initPtr();//初始化下拉刷新
        setListener();
        setOnItemClickListener();
        loadData();//加载数据
        initRxBus();
//        AppManager.getAppManager().addActivity(this);
    }

    protected void initRxBus() {

    }
    /**
     * 加载数据
     */
    protected void loadData() {

    }

    protected void setOnItemClickListener() {

    }
    /**
     * 显示自定义的加载等待对话框
     */
    public void showLoadProgress(String textStr) {
        if (null == mLoadProgress) {
            mLoadProgress = new LoadProgress(this);
            mLoadProgress.setCancelable(true);
            mLoadProgress.setCanceledOnTouchOutside(false);
        }
        if (!this.isFinishing()) {
            if (!mLoadProgress.isShowing()) {
                mLoadProgress.show();

            }
        }
        mLoadProgress.setMsg(textStr);
    }

    /**
     * 显示自定义的等待加载对话框(点击返回可以取消)
     */
    public void showLoadProgress(String textStr, boolean cancelable) {
        if (null == mLoadProgress) {
            mLoadProgress = new LoadProgress(this);
            mLoadProgress.setCanceledOnTouchOutside(false);
        }

        mLoadProgress.setCancelable(cancelable);

        if (!this.isFinishing()) {
            if (!mLoadProgress.isShowing()) {
                mLoadProgress.show();

            }
        }
        mLoadProgress.setMsg(textStr);
    }

    /**
     * dismiss掉自定义等待层对话框
     */
    public void dismissLoadProgress() {
        if (null != mLoadProgress) {
            mLoadProgress.dismiss();
            mLoadProgress = null;
        }
    }


    /**
     * 初始化pull to refresh layout
     */
    protected void initPtr() {

        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr);

        if (ptr != null) {
            // 创建material风格的下拉头部
            MaterialHeader header = new MaterialHeader(this);
            int[] colors = getResources().getIntArray(R.array.ptr_color_array);
            header.setColorSchemeColors(colors);
            header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
            header.setPadding(0, (int)DeviceUtils.dpToPixel(this, 15), 0, (int)DeviceUtils.dpToPixel(this, 15));

            //创建自定义风格的下拉头部
//            CustomPtrHeader header = new CustomPtrHeader(mContext);

            // 设置刷新头部view
            ptr.setHeaderView(header);
            // 设置回调
            ptr.addPtrUIHandler(header);
            // 设置下拉刷新监听
            ptr.setPtrHandler(new PtrHandler() {

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    refreshData();
                }

                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame,
                    View content, View header) {
                    return isCanDoRefresh(frame, content, header);
                }
            });
        }
    }

    /**
     * 判断UI是否满足下拉刷新的下拉条件
     */
    protected boolean isCanDoRefresh(PtrFrameLayout frame,
        View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }


    /**
     * 刷新数据
     */
    protected void refreshData() {

    }

    /**
     * 刷新结束
     */
    protected void refreshComplete() {
        if (ptr != null && ptr.isRefreshing()) {
            ptr.refreshComplete();
        }
    }


    /**
     * @return 返回false则不显示返回navigation icon
     */
    protected boolean isCanBack() {
        return true;
    }

    /**
     * 设置返回按钮
     */
    protected void setupBackIcon() {
        if (isCanBack() && toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back_white);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishActivity();
                }
            });
        }
    }

    protected void finishActivity() {
        finish();
    }
    /**
     * 选择返回
     */
    protected void doBack() {
        finish();
    }

    protected  void setListener(){};
    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppManager.getAppManager().finishActivity(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
            mPresenter = null;
        }
    }
    /**
     * 设置toolbar及标题
     *
     * @param title toolbar标题
     */
    protected void setToolbar(String title) {
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null && title != null) {
                toolbar.setTitle("");
                setSupportActionBar(toolbar);
            }
        } catch (Exception e) {
//            LogUtils.d("method \"setToolbar\" has exception");
        }
        setTitle(title);
    }

    public void setToolbar(int id) {
        setToolbar(getString(id));
    }
    /**
     * 设置activity标题
     *
     * @param title
     */
    public void setTitle(String title) {
        try {
            toolbarTitleTV = (TextView) findViewById(R.id.toolbarTitleTV);
            if (toolbarTitleTV != null) {
                toolbarTitleTV.setText(title);
            }
        } catch (Exception e) {
//            LogUtils.d("method \"setTitle\" has exception");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
