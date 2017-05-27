/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:57
 */
package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.contract.DaggerBaseListContract;
import me.jessyan.mvparms.demo.mvp.model.api.SuccessSubscriber;
import me.jessyan.mvparms.demo.mvp.ui.fragment.DaggerBaseListFragment;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * 类的作用
 *
 * @author: lizubing
 */
@ActivityScope
public class DaggerBaseListPresenter extends
    BasePresenter<DaggerBaseListContract.Model, DaggerBaseListContract.View> {

  private RxErrorHandler mErrorHandler;
  private Application mApplication;
  private int page = 1;

  @Inject
  public DaggerBaseListPresenter(DaggerBaseListContract.Model model, DaggerBaseListContract.View rootView
      , RxErrorHandler handler, Application application) {
    super(model, rootView);
    this.mErrorHandler = handler;
    this.mApplication = application;
  }
  public void requestList(String url,String cacheName , int id,boolean update){
    if (update) page = 1;
    mModel.getListData(url,cacheName,page,id,update)
        .subscribeOn(Schedulers.io())
        .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
        .doOnSubscribe(disposable ->mRootView.showLoading())//显示上拉刷新的进度条
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doAfterTerminate(()->mRootView.hideLoading())
        .compose(((DaggerBaseListFragment) mRootView).<String>bindToLifecycle())//使用RXlifecycle,使subscription和activity一起销毁
        .subscribe(new SuccessSubscriber<String>(mErrorHandler) {
          @Override
          public void onSuccess(String s) {
            page++;
            mRootView.loadListData(s,true);
          }
          @Override
          public void onError(Throwable e) {
            super.onError(e);
            mRootView.loadListData(null,false);
          }
        });
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    this.mErrorHandler = null;
    this.mApplication = null;
  }

}