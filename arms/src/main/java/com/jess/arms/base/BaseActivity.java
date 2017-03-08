package com.jess.arms.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jess.arms.R;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.widget.DefaultAlertDialog;
import com.jess.arms.widget.customhead.CustomPtrHeader;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import javax.inject.Inject;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;
    protected TextView toolbarTitleTV; //标题f
    protected Toolbar toolbar;
    protected PtrClassicFrameLayout ptr;
    protected Context mContext;

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
        setContentView(getLayoutId());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        ComponentInject();//依赖注入
        initViews(savedInstanceState);//初始化页面控件
        setupBackIcon();//设置返回按钮
        initPtr();//初始化下拉刷新
        setListener();
        setOnItemClickListener();
        loadData();//加载数据
        initRxBus();
    }

    protected void initViews(Bundle savedInstanceState) {

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

    protected abstract int getLayoutId();

    /**
     * 初始化pull to refresh layout
     */
    protected void initPtr() {

        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr);

        if (ptr != null) {
            // 创建material风格的下拉头部
          /*  MaterialHeader header = new MaterialHeader(this);
            int[] colors = getResources().getIntArray(R.array.ptr_color_array);
            header.setColorSchemeColors(colors);
            header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
            header.setPadding(0, DeviceUtils.dp2px(this, 15), 0, DeviceUtils.dp2px(this, 15));*/

            //创建自定义风格的下拉头部
            CustomPtrHeader header = new CustomPtrHeader(mContext);

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
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
            mPresenter = null;
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
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
            LogUtils.debugInfo("method \"setToolbar\" has exception");
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
            LogUtils.debugInfo("method \"setTitle\" has exception");
        }
    }

    /**
     * 标准消息提示框（带确定按钮）
     *
     * @param title      标题
     * @param content    内容
     * @param okListener 确定时点击事件
     * @return
     */
    public DefaultAlertDialog showMessageDialog(String title, String content, final View.OnClickListener okListener) {
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(this).builder().setTitle(title);
        alertDialog.setMsg(content)
            .setPositiveButton("确定", okListener)
            .setNegativeButton("取消", null).show();
        return alertDialog;
    }

    /**
     * 单个按钮的消息提示框
     *
     * @param title
     * @param content
     * @param okHint     按钮文字
     * @param okListener 按钮触发的点击事件
     * @return
     */
    public DefaultAlertDialog showMessageDialog(String title, String content, String okHint, final View.OnClickListener okListener) {
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(this).builder().setTitle(title);
        alertDialog.setMsg(content)
            .setPositiveButton(okHint, okListener).show();
        return alertDialog;
    }

    /**
     * 显示消息提示框
     *
     * @param title          标题
     * @param content        内容
     * @param okHint         右边按钮文字
     * @param cancelHint     左边按钮文字
     * @param okListener     右边按钮事件
     * @param cancelListener 左边按钮事件
     * @return
     */
    public DefaultAlertDialog showMessageDialog(String title, String content, String okHint, String cancelHint, final View.OnClickListener okListener,
        final View.OnClickListener cancelListener) {
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(this).builder().setTitle(title);
        alertDialog.setMsg(content)
            .setPositiveButton(okHint, okListener)
            .setNegativeButton(cancelHint, cancelListener).show();
        return alertDialog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
