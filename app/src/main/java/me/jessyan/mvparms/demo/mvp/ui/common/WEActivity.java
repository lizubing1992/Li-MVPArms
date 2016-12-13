package me.jessyan.mvparms.demo.mvp.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.BasePresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.di.component.AppComponent;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected WEApplication mWeApplication;
    @Override
    protected void ComponentInject() {
        mWeApplication = (WEApplication) getApplication();
        setupActivityComponent(mWeApplication.getAppComponent());
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 设置返回按钮
     */
    protected void setupBackIcon(Toolbar toolbar) {
        if (isCanBack() && toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_back_white);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishActivity();
                }
            });
        }
    }

    /**
     * @return 返回false则不显示返回navigation icon
     */
    protected boolean isCanBack() {
        return true;
    }


    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);
}
