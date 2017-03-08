package com.jess.arms.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.R;
import com.jess.arms.utils.StringUtils;

/**
 * 进度对话框
 */
public class LoadProgress extends ProgressDialog {

    private TextView loadingTV;

    public LoadProgress(Context context) {
        super(context, R.style.LoadProgressStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progressdialog);
        /*Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //lp.dimAmount=0.8f; //其他地方的透明度
        window.setAttributes(lp);*/
        loadingTV = (TextView) LoadProgress.this.findViewById(R.id.loadingTV);
    }

    /**
     * 设置加载时的提示文字
     */
    public void setMsg(String msg) {
        if (StringUtils.isNotEmpty(msg)) {
            loadingTV.setVisibility(View.VISIBLE);
            loadingTV.setText(msg);
        } else {
            loadingTV.setVisibility(View.GONE);
        }
    }

}
