/*
 * All rights Reserved, Designed By 农金圈  2017年04月06日15:51
 */
package me.jessyan.mvparms.demo.mvp.model.api;

import com.google.gson.Gson;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseEntity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public abstract class SuccessSubscriber<T> extends ErrorHandleSubscriber<T>{

  public SuccessSubscriber(RxErrorHandler rxErrorHandler) {
    super(rxErrorHandler);
  }

  @Override
  public void onNext(T t) {
//    BaseEntity baseEntity = new Gson().fromJson(t.toString(), BaseEntity.class);
//    Timber.e("baseEntity.getTngou().toString()---"+baseEntity.getTngou().toString());
//    onSuccess();
    try {
      JSONObject json = new JSONObject(t.toString());
      JSONArray array = json.getJSONArray("tngou");
      Timber.e("array"+array.toString());
      onSuccess(array.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public abstract  void onSuccess(String  baseResponse);

}
