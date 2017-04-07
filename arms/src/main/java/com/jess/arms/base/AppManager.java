package com.jess.arms.base;

import android.app.Activity;
import java.util.Stack;

/**
 * App activity manager.
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        return instance;
    }

    //添加Activity到堆栈
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    //获取当前Activity（堆栈中最后一个压入的）
    public Activity currentActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            return activityStack.lastElement();
        }
        return null;
    }

    //结束指定的Activity
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    //结束指定类名的Activity
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 关闭除了MainActivity以外的界面
     *
     * @param cls
     */
    public void finishActivityBesides(Class<?> cls) {
        int index = -1;//那个不被关闭的activity所在的堆栈位置
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (!(activityStack.get(i).getClass().equals(cls))) {
                    activityStack.get(i).finish();
                } else {
                    index = i;//拿到指定的位置
                }
            }
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (i != index) {
                activityStack.remove(i);//删除指定位置以外的activity
            }
        }
    }


    //结束所有Activity
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    //退出应用程序
    public void exitApp() {
        finishAllActivity();
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    //判断一个Activity在不在栈中
    public boolean isActivityAdd(Class mClass) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(mClass)) {
                return true;
            }
        }
        return false;
    }
}
