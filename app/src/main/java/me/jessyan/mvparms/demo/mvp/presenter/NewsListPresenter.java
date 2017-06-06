package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.model.api.SuccessSubscriber;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.NewsListContract;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


@ActivityScope
public class NewsListPresenter extends
    BasePresenter<NewsListContract.Model, NewsListContract.View> {

  private RxErrorHandler mErrorHandler;
  private Application mApplication;
  private ImageLoader mImageLoader;
  private AppManager mAppManager;
  private int page = 1;

  @Inject
  public NewsListPresenter(NewsListContract.Model model, NewsListContract.View rootView
      , RxErrorHandler handler, Application application
      , ImageLoader imageLoader, AppManager appManager) {
    super(model, rootView);
    this.mErrorHandler = handler;
    this.mApplication = application;
    this.mImageLoader = imageLoader;
    this.mAppManager = appManager;
  }

  public void requestList(String cacheName , int id,boolean update){
    if (update) page = 1;
    mModel.getListData("/tnfs/api/list",cacheName,page,id,update)
        .subscribeOn(Schedulers.io())
        .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
        .doOnSubscribe(disposable ->mRootView.showLoading())//显示上拉刷新的进度条
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doAfterTerminate(()->mRootView.hideLoading())
        .compose(((RxFragment) mRootView).bindUntilEvent(FragmentEvent.DESTROY))//使用RXlifecycle,使subscription和activity一起销毁
        .subscribe(new SuccessSubscriber<NewsListEntity>(mErrorHandler) {
          @Override
          public void onSuccess(NewsListEntity entity) {
            page++;
            mRootView.loadListData(entity.getTngou(),true);
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
    this.mAppManager = null;
    this.mImageLoader = null;
    this.mApplication = null;
  }

}