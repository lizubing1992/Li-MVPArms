package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageDetailEntity;
import me.jessyan.mvparms.demo.widget.CircularProgressBar;
import photoview.ImageDownloadListener;
import photoview.PhotoView;

/**
 * Created by xing on 2016/12/7.
 */
public class ImageDetailAdapter extends PagerAdapter {
    private ArrayList<ImageDetailEntity.ListBean> imageDetails;

    @Override
    public int getCount() {
        return imageDetails.size();
    }

    public ImageDetailAdapter(ArrayList<ImageDetailEntity.ListBean> imagUrls) {
        super();
        this.imageDetails = imagUrls;
    }

    public void setImagUrls(ArrayList<ImageDetailEntity.ListBean> imagUrls) {
        this.imageDetails = imagUrls;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View imageLayout = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_detail, container, false);
        PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.photo_view);
        final TextView level = (TextView) imageLayout.findViewById(R.id.level);
        final CircularProgressBar loading = (CircularProgressBar) imageLayout.findViewById(R.id.loading);
        photoView.setImageUri("http://tnfs.tngou.net/img" + imageDetails.get(position).getSrc());
        photoView.setImageDownloadListener(new ImageDownloadListener() {
            @Override
            public void onUpdate(int progress) {
                level.setText(progress + "%");
                loading.setProgress(progress);
                if (progress == 100) {
                    level.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
