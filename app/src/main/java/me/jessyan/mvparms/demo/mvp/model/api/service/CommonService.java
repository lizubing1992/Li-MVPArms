package me.jessyan.mvparms.demo.mvp.model.api.service;

import me.jessyan.mvparms.demo.mvp.model.entity.BaseEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageDetailEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsDetailEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface CommonService {


    /**
     * 图片列表
     * @param page 页数
     * @param id 列表id
     */
    @GET("/tnfs/api/list")
    Observable<ImageEntity> getImageList(@Query("page") int page, @Query("id") int id);

  /**
   * 基类加载列表数据
   * @param url 请求的url
   * @param page 对应页数
   * @param id 请求id
   */
    @GET
    Observable<String> getListData(@Url String url, @Query("page")int page, @Query("id")int id);

    /**
     * 新闻列表
     * @param id 对应的新闻列表id
     * @param page 页数
     */
    @GET("/api/top/list")
    Observable<NewsListEntity> getNewsList(@Query("page") int page, @Query("id") int id);

    /**
     * 图片详情
     * @param id 图片详情id
     */
    @GET("/tnfs/api/show")
    Observable<ImageDetailEntity> getImageDetail(@Query("id") int id);



    /**
     * 新闻详情
     * @param id 对应的新闻id
     */
    @GET("api/top/show")
    Observable<NewsDetailEntity> getNewsDetail(@Query("id") int id);


}
