/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:59
 */
package me.jessyan.mvparms.demo.mvp.ui.fragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import com.jess.arms.di.component.AppComponent;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.base.BaseListFragment;
import me.jessyan.mvparms.demo.di.component.DaggerDaggerBaseListComponent;
import me.jessyan.mvparms.demo.di.module.DaggerBaseListModule;
import me.jessyan.mvparms.demo.mvp.contract.DaggerBaseListContract;
import me.jessyan.mvparms.demo.mvp.presenter.DaggerBaseListPresenter;
import me.jessyan.mvparms.demo.widget.EmptyLayout;
import timber.log.Timber;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class DaggerBaseListFragment extends BaseListFragment<DaggerBaseListPresenter> implements
    DaggerBaseListContract.View {
  @BindView(R.id.ptr)
  PtrClassicFrameLayout ptr;
  @BindView(R.id.listView)
  ListView listView;
  @BindView(R.id.emptyLayout)
  EmptyLayout emptyLayout;
  protected int id = 1;
  protected String cacheName = "";
  protected String url = "";


  @Override
  public void setupFragmentComponent(AppComponent appComponent) {
    DaggerDaggerBaseListComponent
        .builder()
        .appComponent(appComponent)
        .daggerBaseListModule(new DaggerBaseListModule(this))//请将DaggerBaseListModule()第一个首字母改为小写
        .build()
        .inject(this);
  }
  @Override
  protected void initView(View rootView) {
    setPageSize(20);
    mListView = listView;
    mEmptyLayout = emptyLayout;
    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
    ((ListView)mListView).setDividerHeight(10);
    mListView.setOnScrollListener(mScrollListener);
  }
  @Override
  public void showLoading() {
    Timber.tag(TAG).w("showLoading");
  }

  @Override
  public void hideLoading() {
    refreshFinish();
  }

  @Override
  public void showMessage(@NonNull String message) {
    checkNotNull(message);
    WEApplication.showToast(message);
  }

  @Override
  public void launchActivity(Intent intent) {

  }

  @Override
  public void killMyself() {

  }

  @Override
  public void loadListData(String  list,boolean isSuccess) {

  }


  @Override
  public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.activity_user,null,false);
    return rootView;
  }

  @Override
  protected void requestList(boolean isCache) {
    mPresenter.requestList(url,cacheName, id, isCache);
   }

  }
