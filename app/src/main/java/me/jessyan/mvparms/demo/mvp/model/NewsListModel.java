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

import me.jessyan.mvparms.demo.mvp.contract.NewsListContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;


@ActivityScope
public class NewsListModel extends BaseModel implements NewsListContract.Model {

  private Gson mGson;
  private Application mApplication;

  @Inject
  public NewsListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
    super(repositoryManager);
    this.mGson = gson;
    this.mApplication = application;
  }

  @Override
  public Observable<NewsListEntity> getListData(String url,String cacheName, int page, int id, boolean update) {
    Observable<NewsListEntity> imageList = mRepositoryManager.obtainRetrofitService(CommonService.class)
        .getNewsList(page, id);
    //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
    return mRepositoryManager.obtainCacheService(CommonCache.class)
        .getNewsList(imageList
            , new DynamicKey(cacheName + page)
            , new EvictDynamicKey(update))
        .flatMap(new Function<Reply<NewsListEntity>, ObservableSource<NewsListEntity>>() {
          @Override
          public ObservableSource<NewsListEntity> apply(@NonNull Reply<NewsListEntity> stringReply) throws Exception {
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