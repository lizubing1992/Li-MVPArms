/*
 * All rights Reserved, Designed By 农金圈  2017年05月27日11:16
 */
package me.jessyan.mvparms.demo.app;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jess.arms.base.BaseApplication;
import me.jessyan.mvparms.demo.R;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class WEApplication extends BaseApplication{
  private static WEApplication mApplication;

  @Override
  public void onCreate() {
    super.onCreate();
    mApplication = this;
  }

  /**
   * 返回上下文
   *
   * @return
   */
  public static Context getContext() {
    return mApplication;
  }
  /**
   * 返回上下文
   *
   * @return
   */
  public static WEApplication getInstance() {
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
