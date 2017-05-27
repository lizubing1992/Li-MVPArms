package me.jessyan.mvparms.demo.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import me.jessyan.mvparms.demo.R;

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
        if (isNotEmpty(msg)) {
            loadingTV.setVisibility(View.VISIBLE);
            loadingTV.setText(msg);
        } else {
            loadingTV.setVisibility(View.GONE);
        }
    }

    /**
     * 是否 不为空字符串 或 null
     *
     * @param s
     * @return
     */
    public  boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

}
