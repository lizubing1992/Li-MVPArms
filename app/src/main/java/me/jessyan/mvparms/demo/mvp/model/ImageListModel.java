package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.ImageListContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;


@ActivityScope
public class ImageListModel extends BaseModel implements ImageListContract.Model {

  private Gson mGson;
  private Application mApplication;

  @Inject
  public ImageListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
    super(repositoryManager);
    this.mGson = gson;
    this.mApplication = application;
  }

  @Override
  public Observable<ImageEntity> getListData(String url,String cacheName, int page, int id, boolean update) {
    Observable<ImageEntity> imageList = mRepositoryManager.obtainRetrofitService(CommonService.class)
        .getImageList(page, id);
    //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
    return mRepositoryManager.obtainCacheService(CommonCache.class)
        .getImageList(imageList
            , new DynamicKey(cacheName + page)
            , new EvictDynamicKey(update))
        .flatMap(new Function<Reply<ImageEntity>, ObservableSource<ImageEntity>>() {
          @Override
          public ObservableSource<ImageEntity> apply(@NonNull Reply<ImageEntity> stringReply) throws Exception {
            return Observable.just(stringReply.getData());
          }
        });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.mGson = null;
    this.mApplication = null;
  }

}