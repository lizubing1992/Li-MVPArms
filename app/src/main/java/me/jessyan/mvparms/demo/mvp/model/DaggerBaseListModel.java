/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:55
 */
package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import javax.inject.Inject;
import me.jessyan.mvparms.demo.mvp.contract.DaggerBaseListContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import timber.log.Timber;


/**
 * 类的作用
 *
 * @author: lizubing
 */
@ActivityScope
public class DaggerBaseListModel extends BaseModel implements
    DaggerBaseListContract.Model {
  @Inject
  public DaggerBaseListModel(IRepositoryManager repositoryManager) {
    super(repositoryManager);
  }

  @Override
  public Observable<String> getListData(String url,String cacheName, int page, int id, boolean update) {
    Observable<String> imageList = mRepositoryManager.obtainRetrofitService(CommonService.class)
        .getListData(url,page, id);
    //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
    return mRepositoryManager.obtainCacheService(CommonCache.class)
        .getListData(imageList
            , new DynamicKey(cacheName + page)
            , new EvictDynamicKey(update))
        .flatMap(new Function<Reply<String>, ObservableSource<String>>() {
          @Override
          public ObservableSource<String> apply(@NonNull Reply<String> stringReply) throws Exception {
            return Observable.just(stringReply.getData());
          }
        });
  }

}