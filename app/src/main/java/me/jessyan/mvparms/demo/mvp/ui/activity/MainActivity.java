package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.fragment.ImageFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.ImageListFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.NewsFragment;
import me.jessyan.mvparms.demo.mvp.ui.fragment.UserFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private ArrayList<BaseFragment> fragments;
    private static long DOUBLE_CLICK_TIME = 0L;
    @Override
    protected void ComponentInject() {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }

    @Override
    protected void initData() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_picture, "图片").setActiveColorResource(R.color.picture_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_news, "新闻").setActiveColorResource(R.color.news_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我的").setActiveColorResource(R.color.my_color))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    UiUtils.SnackbarText(getString(R.string.double_click_exit));
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, new ImageFragment());
        transaction.commit();
    }

    private ArrayList<BaseFragment> getFragments() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new ImageFragment());
        fragments.add(new NewsFragment());
        fragments.add(new UserFragment());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                BaseFragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.show(fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                BaseFragment fragment = fragments.get(position);
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
