package me.jessyan.mvparms.demo.utils;

import android.view.View;
import com.facebook.drawee.view.SimpleDraweeView;
import me.jessyan.mvparms.demo.app.FrescoHelper;

/**
 * Created by administrator on 2017/3/8.
 */

public class FrescoImageLoaderStrategy implements ImageLoaderStrategy {

  @Override
  public void showImage(View v, String url, ImageLoaderOptions options) {
    if (v instanceof SimpleDraweeView) {
      SimpleDraweeView view = (SimpleDraweeView) v;

      FrescoHelper.displayImageview(view,url,false,0.0f);

    }
  }

  @Override
  public void showImage(View v, int drawable, ImageLoaderOptions options) {
  }
}
