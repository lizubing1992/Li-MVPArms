package com.jess.arms.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.R;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.DefaultAlertDialog;
import com.jess.arms.widget.customhead.CustomPtrHeader;
import com.trello.rxlifecycle.components.support.RxFragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import org.simple.eventbus.EventBus;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jess on 2015/12/8.
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {
    protected BaseActivity mActivity;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;
    public static final int STATE_NONE = 0; //无任何动作
    public static final int STATE_REFRESH = 1; //刷新状态
    public static final int STATE_LOAD_MORE = 2; //加载更多状态
    protected PtrClassicFrameLayout ptr;
    protected View rootView;
    public BaseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //绑定到butterknife
        mActivity = (BaseActivity) getActivity();
        if (getLayoutId() == 0) {
            throw new RuntimeException(
                "no layout can be set content view");
        }
        rootView = inflater.inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initPtr(rootView);
        ComponentInject();
        loadData();
        initRxBus();
        setListener();
        return rootView;
    }


    protected void initRxBus() {

    }
    /**
     * 初始化pull to refresh layout
     */
    protected void initPtr(View view) {

        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);

        if (ptr != null) {
            /*//创建material风格的下拉头部
            MaterialHeader header = new MaterialHeader(mContext);
            int[] colors = getResources().getIntArray(R.array.ptr_color_array);
            header.setColorSchemeColors(colors);
            header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
            header.setPadding(0, DeviceUtils.dp2px(mContext, 15), 0, DeviceUtils.dp2px(mContext, 15));*/

            //创建自定义风格的下拉头部
            CustomPtrHeader header = new CustomPtrHeader(mActivity);

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
    public void refreshData() {

    }

    protected LayoutInflater getLayoutInflater() {
        return mActivity.getLayoutInflater();
    }


    /**
     * 判断UI是否满足下拉刷新的下拉条件
     */
    protected boolean isCanDoRefresh(PtrFrameLayout frame,
        View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    protected void ptrRefreshComplete() {
        if (ptr != null && ptr.isRefreshing()) {
            ptr.refreshComplete();
        }
    }
    // 初始化一些控件
    protected void initView(View rootView) {

    }

    protected abstract int getLayoutId();



    protected  void setListener(){};

    protected void loadData() {

    }
    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
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
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(getActivity()).builder().setTitle(title);
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
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(getActivity()).builder().setTitle(title);
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
        final DefaultAlertDialog alertDialog = new DefaultAlertDialog(getActivity()).builder().setTitle(title);
        alertDialog.setMsg(content)
            .setPositiveButton(okHint, okListener)
            .setNegativeButton(cancelHint, cancelListener).show();
        return alertDialog;
    }

    public boolean onBackPressed() {
        return false;
    }

}
