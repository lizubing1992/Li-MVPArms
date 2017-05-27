package me.jessyan.mvparms.demo.mvp.model.api.cache;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;

/**
 * Created by jess on 8/30/16 13:53
 * Contact with jess.yan.effort@gmail.com
 */
public interface CommonCache {




    Observable<Reply<ImageEntity>> getImageList(Observable<ImageEntity> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    Observable<Reply<String>> getListData(Observable<String> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    Observable<Reply<NewsListEntity>> getNewsList(Observable<NewsListEntity> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

}
