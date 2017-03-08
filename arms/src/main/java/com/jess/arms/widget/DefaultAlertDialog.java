package com.jess.arms.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jess.arms.R;

/**
 * 消息提示对话框
 */
public class DefaultAlertDialog {

    private Window window;
    private Context context;
    private Dialog dialog;
    private TextView txt_title, txt_msg, btn_neg, btn_pos;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private ProgressBar downPB;
    private LinearLayout dialogRootLL, downLL, btnLL;
    private ImageView cancelIV;
    private TextView downloadStateTV;
    private View view;

    public DefaultAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    /**
     * 构建对话框对象
     *
     * @return
     */
    public DefaultAlertDialog builder() {
        // 获取Dialog布局
        view = LayoutInflater.from(context).inflate(R.layout.view_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        btnLL = (LinearLayout) view.findViewById(R.id.btnLL);
        downLL = (LinearLayout) view.findViewById(R.id.downLL);
        downPB = (ProgressBar) view.findViewById(R.id.downPB);
        dialogRootLL = (LinearLayout) view.findViewById(R.id.dialogRootLL);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (TextView) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (TextView) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        downloadStateTV = (TextView) view.findViewById(R.id.downloadStateTV);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        dialogRootLL.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.80), LayoutParams.WRAP_CONTENT));

        // 设置右上角X按钮
        cancelIV = (ImageView) view.findViewById(R.id.cancelIV);
        cancelIV.setVisibility(View.GONE);
        cancelIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // 设置弹窗动画
        windowDeploy();
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        setLayout();
        dialog.show();
    }

    /**
     * 取消
     */
    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * 配置布局
     */
    private void setLayout() {

        if (!showTitle && !showMsg) {
            txt_title.setText("温馨提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_neg.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否可以点击对话框其它地方取消
     *
     * @param cancel
     * @return
     */
    public DefaultAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 隐藏取消按钮
     *
     * @return
     */
    public DefaultAlertDialog hideNegBtn() {
        showNegBtn = false;
        if (btn_neg != null) {
            btn_neg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public DefaultAlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("温馨提示");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    /**
     * 设置消息内容
     *
     * @param msg
     * @return
     */
    public DefaultAlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("");
        } else {
            //msg = StringUtils.toDBC(msg);//把半角字符改为全角的
            txt_msg.setText(msg);
        }
        return this;
    }

    /**
     * 设置对话框确定按钮，点击自动取消对话框
     *
     * @param text
     * @param listener
     * @return
     */
    public DefaultAlertDialog setPositiveButton(String text,
                                                final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    /**
     * 设置对话框确定按钮，且点击不自动取消对话框
     *
     * @param text
     * @param listener
     * @param isAutoDismiss
     * @return
     */
    public DefaultAlertDialog setPositiveButton(String text,
                                                final OnClickListener listener, final boolean isAutoDismiss) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAutoDismiss) {
                    dialog.dismiss();
                }
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    /**
     * 设置对话框取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public DefaultAlertDialog setNegativeButton(String text,
                                                final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public TextView getMsgTV() {
        return txt_msg;
    }

    /**
     * 设置对话款消失监听
     *
     * @param listener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
    }

    /**
     * 显示下载进度
     *
     * @param progress
     * @param max
     */
    public void setDownProgress(long progress, long max) {
        downPB.setVisibility(View.VISIBLE);
        downPB.setMax((int) max);
        downPB.setProgress((int) progress);
    }

    /**
     * 设置下载状态
     *
     * @param state
     */
    public void setDownloadState(String state) {
        downloadStateTV.setText(state);
    }

    /**
     * 显示下载相关的进度UI
     */
    public void showDownLL() {
        btnLL.setVisibility(View.INVISIBLE);
        downLL.setVisibility(View.VISIBLE);
        cancelIV.setVisibility(View.VISIBLE);
    }

    /**
     * 设置窗口显示动画
     */
    public void windowDeploy() {
        window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.alertAnim); //设置窗口弹出动画
        //window.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        window.setAttributes(wl);
    }

}
