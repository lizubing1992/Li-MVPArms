package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.NewsListContract;
import me.jessyan.mvparms.demo.mvp.model.NewsListModel;


@Module
public class NewsListModule {

  private NewsListContract.View view;

  /**
   * 构建NewsListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
   */
  public NewsListModule(NewsListContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  NewsListContract.View provideNewsListView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  NewsListContract.Model provideNewsListModel(NewsListModel model) {
    return model;
  }
}