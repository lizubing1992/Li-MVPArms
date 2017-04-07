/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:55
 */
package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import javax.inject.Inject;
import me.jessyan.mvparms.demo.mvp.contract.DaggerBaseListContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CacheManager;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.api.service.ServiceManager;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;


/**
 * 类的作用
 *
 * @author: lizubing
 */
@ActivityScope
public class DaggerBaseListModel extends BaseModel<ServiceManager, CacheManager> implements
    DaggerBaseListContract.Model {

  private Gson mGson;
  private Application mApplication;
  private CommonService mCommonService;
  private CommonCache mCommonCache;

  @Inject
  public DaggerBaseListModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson,
      Application application) {
    super(serviceManager, cacheManager);
    this.mGson = gson;
    this.mApplication = application;
    this.mCommonService = mServiceManager.getCommonService();
    this.mCommonCache = mCacheManager.getCommonCache();
  }

  @Override
  public void onDestory() {
    super.onDestory();
    this.mGson = null;
    this.mApplication = null;
  }

  @Override
  public Observable<String> getListData(String url,String cacheName, int page, int id, boolean update) {
    Observable<String> imageList = mCommonService
        .getListData(url,page, id);
    //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
    return mCommonCache
        .getListData(imageList
            , new DynamicKey(cacheName + page)
            , new EvictDynamicKey(update))
        .flatMap(new Func1<Reply<String>, Observable<String>>() {
          @Override
          public Observable<String> call(Reply<String> listReply) {
            return Observable.just(listReply.getData());
          }
        });
  }

}