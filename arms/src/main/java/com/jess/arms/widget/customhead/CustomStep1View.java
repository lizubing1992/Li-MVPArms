package com.jess.arms.widget.customhead;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.jess.arms.R;

/**
 * Created by Shaw on 2016/7/11.
 * 下拉刷新自定义头部下拉第一步显示图片缩放的View
 */
public class CustomStep1View extends View {

    private Bitmap initialBitmap;//最初的图片
    private int measuredWidth;//计算最后的宽度
    private int measuredHeight;//计算最后的高度
    private float mCurrentScale;//当前缩放倍数：0-1
    private Bitmap scaledBitmap;//缩放后的图片

    public CustomStep1View(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomStep1View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomStep1View(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        //这个就是那个椭圆形图片
        initialBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_ptr1));
    }

    /**
     * 在onLayout里面获得测量后View的宽高
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        scaledBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth,
                measuredWidth * initialBitmap.getHeight() / initialBitmap.getWidth(), true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个方法是对画布进行缩放，从而达到椭圆形图片的缩放，第一个参数为宽度缩放比例，第二个参数为高度缩放比例，
        canvas.scale(mCurrentScale, mCurrentScale, measuredWidth / 2, measuredHeight / 2);
        //将等比例缩放后的椭圆形画在画布上面
        canvas.drawBitmap(scaledBitmap, 0, 0, null);
    }

    /**
     * 设置缩放比例，从0到1  0为最小 1为最大
     *
     * @param scale
     */
    public void setCurrentScale(float scale) {
        mCurrentScale = scale;
    }
}
