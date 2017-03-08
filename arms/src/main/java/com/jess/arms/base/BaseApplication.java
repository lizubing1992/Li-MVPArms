package com.jess.arms.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jess.arms.R;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.http.GlobeHttpHandler;

import java.util.LinkedList;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import okhttp3.Interceptor;

/**
 * 本项目由
 * mvp
 * +dagger2
 * +retrofit
 * +rxjava
 * +androideventbus
 * +butterknife组成
 */
public abstract class BaseApplication extends Application {
    static private BaseApplication mApplication;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mClientModule = ClientModule//用于提供okhttp和retrofit的单列
                .buidler()
                .baseurl(getBaseUrl())
                .globeHttpHandler(getHttpHandler())
                .interceptors(getInterceptors())
                .responseErroListener(getResponseErroListener())
                .build();
        this.mAppModule = new AppModule(this);//提供application
    }

    /**
     * 提供基础url给retrofit
     *
     * @return
     */
    protected abstract String getBaseUrl();

    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }



    /**
     * 这里可以提供一个全局处理http响应结果的处理类,
     * 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
     * 默认不实现,如果有需求可以重写此方法
     *
     * @return
     */
    protected GlobeHttpHandler getHttpHandler() {
        return null;
    }

    /**
     * 用来提供interceptor,如果要提供额外的interceptor可以让子application实现此方法
     *
     * @return
     */
    protected Interceptor[] getInterceptors() {
        return null;
    }


    /**
     * 用来提供处理所有错误的监听
     * 如果要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法)
     * 则让子application重写此方法
     * @return
     */
    protected ResponseErroListener getResponseErroListener() {
        return new ResponseErroListener() {
            @Override
            public void handleResponseError(Context context, Exception e) {

            }
        };
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

    private static String lastMsg = "";
    private static long lastTime;
    private static Toast toast;

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
            | Gravity.TOP);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_SHORT, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_SHORT, icon, Gravity.FILL_HORIZONTAL
            | Gravity.TOP);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
            | Gravity.TOP);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
            | Gravity.TOP, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.FILL_HORIZONTAL
            | Gravity.TOP);
    }

    public static void showToast(int message, int duration, int icon,
        int gravity) {
        showToast(mApplication.getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
        int gravity, Object... args) {
        showToast(mApplication.getString(message, args), duration, icon, gravity);
    }

    /**
     * 显示自定义的toast
     *
     * @param message  toast内容
     * @param duration toast延迟
     * @param icon
     * @param gravity
     */
    public static void showToast(String message, int duration, int icon,
        int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastMsg)
                || Math.abs(time - lastTime) > 2000) {
                View view = LayoutInflater.from(mApplication).inflate(
                    R.layout.layout_toast, null);
                ((TextView) view.findViewById(R.id.toastTV)).setText(message);
                if (icon != 0) {
                    ImageView iconIV = (ImageView) view.findViewById(R.id.iconIV);
                    iconIV.setVisibility(View.VISIBLE);
                    iconIV.setImageResource(icon);
                }
                if (toast == null) {
                    toast = new Toast(mApplication);
                }
                toast.setView(view);
                toast.setDuration(duration);
                toast.show();

                lastMsg = message;
                lastTime = System.currentTimeMillis();
            }
        }
    }

}
