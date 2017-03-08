package me.jessyan.mvparms.demo.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by administrator on 2017/3/8.
 */

public class ImageLoaderStrategyManager implements ImageLoaderStrategy {

  private static final ImageLoaderStrategyManager INSTANCE = new ImageLoaderStrategyManager();
  private ImageLoaderStrategy imageLoader;
  private ImageLoaderStrategyManager(){
    //默认使用Glide
    imageLoader=new GlideImageLoaderStrategy();
  }
  public static ImageLoaderStrategyManager getInstance(){
    return INSTANCE;
  }
  //可实时替换图片加载框架
  public void setImageLoader(ImageLoaderStrategy loader) {
    if (loader != null) {
      imageLoader=loader;
    }
  }

  @Override
  public void showImage(@NonNull View mView, @NonNull String mUrl, @Nullable ImageLoaderOptions options) {

    imageLoader.showImage(mView,mUrl,options);
  }


  @Override
  public void showImage(@NonNull  View mView, @NonNull int mDraeable, @Nullable ImageLoaderOptions options) {
    imageLoader.showImage(mView,mDraeable,options);
  }

}
