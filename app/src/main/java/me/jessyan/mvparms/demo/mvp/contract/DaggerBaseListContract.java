/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:44
 */
package me.jessyan.mvparms.demo.mvp.contract;

import com.jess.arms.mvp.BaseView;
import rx.Observable;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public interface DaggerBaseListContract {

  //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
  interface View extends BaseView {
     void loadListData(String data ,boolean isSuccess);
  }

  //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
  interface Model {
    Observable<String> getListData(String url,String cacheName ,int page, int id,boolean update);
  }
}