package me.jessyan.mvparms.demo.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DeviceUtils;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import javax.inject.Inject;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.widget.LoadProgress;

/**
 * Created by jess on 2015/12/8.
 */
public abstract class BaseRefreshFragment<P extends BasePresenter> extends BaseFragment<P> {
    protected BaseRefreshActivity mActivity;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    public static final int STATE_NONE = 0; //无任何动作<
    public static final int STATE_REFRESH = 1; //刷新状态
    public static final int STATE_LOAD_MORE = 2; //加载更多状态
    protected PtrClassicFrameLayout ptr;
    protected View rootView;
    protected LoadProgress mLoadProgress = null;
    public BaseRefreshFragment() {

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity = (BaseRefreshActivity) getActivity();
        initView(rootView);
        initPtr(rootView);
        loadData();
        initRxBus();
        setListener();
    }

    @Override
    public void setData(Object data) {

    }

    protected void initRxBus() {

    }
    /**
     * 初始化pull to refresh layout
     */
    protected void initPtr(View view) {

        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);

        if (ptr != null) {
            //创建material风格的下拉头部
            MaterialHeader header = new MaterialHeader(mActivity);
            int[] colors = getResources().getIntArray(R.array.ptr_color_array);
            header.setColorSchemeColors(colors);
            header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
            header.setPadding(0, (int)DeviceUtils.dpToPixel(mActivity, 15), 0, (int)DeviceUtils
                .dpToPixel(mActivity, 15));

            //创建自定义风格的下拉头部
           /* CustomPtrHeader header = new CustomPtrHeader(mActivity);*/

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

    /**
     * 显示自定义的加载等待对话框
     */
    public void showLoadProgress(String textStr) {
        if (null == mLoadProgress) {
            mLoadProgress = new LoadProgress(mActivity);
            mLoadProgress.setCancelable(true);
            mLoadProgress.setCanceledOnTouchOutside(false);
        }
        if (!this.isDetached()) {
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
            mLoadProgress = new LoadProgress(mActivity);
            mLoadProgress.setCanceledOnTouchOutside(false);
        }

        mLoadProgress.setCancelable(cancelable);

        if (!this.isDetached()) {
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

    protected  void setListener(){}

    protected void loadData() {

    }

    public boolean onBackPressed() {
        return false;
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
    }


}
