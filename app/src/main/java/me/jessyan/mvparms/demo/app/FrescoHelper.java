package me.jessyan.mvparms.demo.app;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import me.jessyan.mvparms.demo.R;

public class FrescoHelper {
	//默认加载图片和失败图片
  public static Drawable sPlaceholderDrawable;
  public static Drawable sErrorDrawable;
	/**
	 * 图像选项类
	 * @param isRound 是否圆角
	 * @param radius  圆角角度
	 * @return
	 */
	public static GenericDraweeHierarchy getImageViewHierarchy(Resources resources, boolean isRound, float radius) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.drawable.ic_loading));
		builder.setPlaceholderImage(resources.getDrawable(R.drawable.ic_loading));
		builder.setFadeDuration(300);
		if (isRound) {
			RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
			builder.setRoundingParams(roundingParams);
		}
		return builder.build();
	}
	/**
	 * splash界面
	 * @param resources Resources
	 */
	public static GenericDraweeHierarchy getSplashViewHierarchy(Resources resources) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.drawable.transparent));
		builder.setPlaceholderImage(resources.getDrawable(R.drawable.transparent));
		builder.setFadeDuration(300);
		return builder.build();
	}
	/**
	 * 图像选项类
	 * @param resources  Resources
	 * @param isRound 是否圆角
	 * @param radius 圆角角度
	 */
	public static GenericDraweeHierarchy getImageProgHierarchy(Resources resources, boolean isRound, float radius) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.drawable.ic_loading));
		builder.setPlaceholderImage(resources.getDrawable(R.drawable.ic_loading));
//		builder.setProgressBarImage(new CustomProgressbarDrawable());
		builder.setFadeDuration(300);
		if (isRound) {
			RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
			builder.setRoundingParams(roundingParams);
		}
		return builder.build();
	}

	/**
	 * 图像选项类
	 * @param resources Resources
	 * @param isCircle 是否圆圈
	 */
	public static GenericDraweeHierarchy getImageViewHierarchy(Resources resources, boolean isCircle) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.drawable.defalut_avatar), ScaleType.FIT_XY);
		builder.setPlaceholderImage(resources.getDrawable(R.drawable.defalut_avatar));
		builder.setFadeDuration(300);
		if (isCircle) {
			RoundingParams circleParams = RoundingParams.asCircle();
			circleParams.setBorder(R.color.black_avart_magin, 1.0f);
			circleParams.setRoundAsCircle(true);
			builder.setRoundingParams(circleParams);
		}
		return builder.build();
	}

	/**
	 * 图像选项类
	 * @param uri 图片路径
	 * @param oldController DraweeView.getoldcontroller
	 * @param controllerListener 监听
	 * @return
	 */
	public static DraweeController getImageViewController(String uri, DraweeController oldController,
			ControllerListener<ImageInfo> controllerListener) {
		PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
		if (!TextUtils.isEmpty(uri)) {
//			Logger.d("StringUtils.utf8Encode(uri)"+StringUtils.utf8Encode(uri));
			builder.setUri(Uri.parse(uri));
		}
		if (oldController != null) {
			builder.setOldController(oldController);
		}
		if (controllerListener != null) {
			builder.setControllerListener(controllerListener);
		}
		return builder.build();
	}

	/**
	 * 加载图片
	 * @param draweeView SimpleDraweeView
	 * @param uri  地址url
	 * @param isRound 是否是圆角
	 * @param radius 圆角的角弧度
	 */
	public static void displayImageview(SimpleDraweeView draweeView, String uri,boolean isRound, float radius) {
		draweeView.setHierarchy(getImageViewHierarchy(WEApplication.getContext().getResources(), isRound, radius));
		draweeView.setController(getImageViewController(uri, draweeView.getController(), null));
	}

	/**
	 * splash 开屏界面中的背景设置
	 *      显示不需要默认图
	 * @param draweeView SimpleDraweeView
	 * @param uri 地址url
	 */
	public static void displaySplash(SimpleDraweeView draweeView, String uri) {
		draweeView.setHierarchy(getSplashViewHierarchy(WEApplication.getContext().getResources()));
		draweeView.setController(getImageViewController(uri, draweeView.getController(), null));
	}
	/**
	 * 显示头像图
	 * 头像为“”时显示默认头像
	 * @param circle 显示头像
	 */
	public static void displayImage2Cir(SimpleDraweeView draweeView, String uri,boolean circle) {
		draweeView.setHierarchy(getImageViewHierarchy(WEApplication.getContext().getResources(), circle));
		if("".equals(uri)){
			uri = "res://drawable-hdpi/" + R.drawable.defalut_avatar;
		}
		draweeView.setController(getImageViewController(uri, draweeView.getController(), null));
	}

	public static void displayImageResize(SimpleDraweeView view ,String url,int width,int height,float ratio){
		Uri uri = Uri.parse(url);
		view.setAspectRatio(ratio);
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
				.setResizeOptions(new ResizeOptions(width, height))
				.build();
		PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
				.setOldController(view.getController())
				.setImageRequest(request)
				.build();
		view.setHierarchy(getImageViewHierarchy(WEApplication.getContext().getResources(), false, 0));
		view.setController(controller);
	}
}
