package com.jess.arms.widget.customhead;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.jess.arms.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Shaw on 2016/7/11.
 * 自定义下拉刷新头部
 */
public class CustomPtrHeader extends FrameLayout implements PtrUIHandler {

    private AnimationDrawable animationDrawable;
    private ImageView loadingIV;//刷新时显示的View
    private CustomStep1View s1View;//没有刷新缩放时显示的View

    public CustomPtrHeader(Context context) {
        super(context);
        init(context);
    }

    public CustomPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_ptr_header, null);
        s1View = (CustomStep1View) view.findViewById(R.id.s1View);
        loadingIV = (ImageView) view.findViewById(R.id.loadingIV);
        loadingIV.setImageResource(R.drawable.custom_ptr_loading);
        animationDrawable = (AnimationDrawable) loadingIV.getDrawable();
        addView(view);
    }

    /**
     * 下拉弹回重置时的UI
     *
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        animationDrawable.selectDrawable(0);//重置图片动画
        setupLoading(false);
    }

    /**
     * 准备刷新的UI
     *
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        setupLoading(false);
    }

    /**
     * 开始刷新时的UI
     *
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        animationDrawable.start();
        setupLoading(true);
    }

    /**
     * 刷结束后的UI
     *
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        animationDrawable.stop();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float percent = Math.min(1f, ptrIndicator.getCurrentPercent());
        if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            s1View.setAlpha(percent);
            s1View.setCurrentScale(percent);
            s1View.postInvalidate();
            //invalidate();
        }
    }

    private void setupLoading(boolean isRefresing) {
        if (isRefresing) {
            s1View.setVisibility(View.GONE);
            loadingIV.setVisibility(View.VISIBLE);
        } else {
            s1View.setVisibility(View.VISIBLE);
            loadingIV.setVisibility(View.GONE);
        }
    }
}