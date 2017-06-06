package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.ImageListContract;
import me.jessyan.mvparms.demo.mvp.model.ImageListModel;


@Module
public class ImageListModule {

  private ImageListContract.View view;

  /**
   * 构建ImageListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
   */
  public ImageListModule(ImageListContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ImageListContract.View provideImageListView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ImageListContract.Model provideImageListModel(ImageListModel model) {
    return model;
  }
}