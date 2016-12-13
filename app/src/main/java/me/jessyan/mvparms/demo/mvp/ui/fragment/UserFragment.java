package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.activity.WebViewActivity;

/**
 * Created by xing on 2016/12/9.
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.tv_game_time)
    ImageView tvGameTime;
    @BindView(R.id.ll_author)
    LinearLayout llAuthor;
    @BindView(R.id.ll_mainPage)
    LinearLayout llMainPage;
    @BindView(R.id.ll_introduce)
    LinearLayout llIntroduce;
    @BindView(R.id.ll_demonstrate)
    LinearLayout llDemonstrate;

    @Override
    protected void ComponentInject() {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user, null, false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void showWebView(String title,String url){
        Intent intent = new Intent(mActivity, WebViewActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    protected void setListener() {
        super.setListener();
        llAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebView("关于作者","https://github.com/lizubing1992");
            }
        });
        llMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebView("项目主页","https://github.com/lizubing1992");
            }
        });
        llIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebView("项目介绍","file:///android_asset/project_description.html");
            }
        });
        llDemonstrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebView("开源说明","file:///android_asset/open_source.html");
            }
        });

    }
}
