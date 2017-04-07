/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:59
 */
package me.jessyan.mvparms.demo.di.module;

import com.google.gson.Gson;
import android.app.Application;
import com.jess.arms.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.demo.mvp.contract.DaggerBaseListContract;
import me.jessyan.mvparms.demo.mvp.model.DaggerBaseListModel;

/**
 * 类的作用
 *
 * @author: lizubing
 */
@Module
public class DaggerBaseListModule {

  private DaggerBaseListContract.View view;

  /**
   * 构建DaggerBaseListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
   */
  public DaggerBaseListModule(DaggerBaseListContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  DaggerBaseListContract.View provideDaggerBaseListView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  DaggerBaseListContract.Model provideDaggerBaseListModel(DaggerBaseListModel model) {
    return model;
  }
}