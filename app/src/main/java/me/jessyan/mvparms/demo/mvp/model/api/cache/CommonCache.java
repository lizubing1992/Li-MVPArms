package me.jessyan.mvparms.demo.mvp.model.api.cache;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.Reply;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {




    Observable<Reply<ImageEntity>> getImageList(Observable<ImageEntity> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    Observable<Reply<String>> getListData(Observable<String> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    Observable<Reply<NewsListEntity>> getNewsList(Observable<NewsListEntity> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

}
