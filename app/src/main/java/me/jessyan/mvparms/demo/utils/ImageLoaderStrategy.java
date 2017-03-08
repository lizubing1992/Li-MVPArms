package me.jessyan.mvparms.demo.utils;

import android.view.View;

/**
 * Created by administrator on 2017/3/8.
 */

public interface ImageLoaderStrategy {
  void showImage(View v,  String url, ImageLoaderOptions options);
  void showImage(View v, int drawable,ImageLoaderOptions options);

}
