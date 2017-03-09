package me.jessyan.mvparms.demo.mvp.ui.adapter;

import static android.R.attr.data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import com.jess.arms.utils.KnifeUtil;
import java.util.List;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.FrescoHelper;
import me.jessyan.mvparms.demo.base.BaseListAdapter;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity.TngouBean;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageListAdapter.ViewHolder;
import me.jessyan.mvparms.demo.mvp.ui.holder.NewsListItemHolder;
import me.jessyan.mvparms.demo.utils.MyUtils;
import rx.Observable;

/**
 * Created by xing on 2016/12/7.
 */
public class NewsListAdapter extends BaseListAdapter {

    private Context context;

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            context = parent.getContext();
            convertView = getLayoutInflater(context).inflate(R.layout.item_news_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (dataList != null && dataList.size() > 0) {
            if (dataList.get(position) instanceof NewsListEntity.TngouBean) {
                NewsListEntity.TngouBean data = (NewsListEntity.TngouBean ) dataList.get(position);
                Observable.just(data.getTitle())
                    .subscribe(RxTextView.text(holder.newsSummaryTitleTv));
                Observable.just(data.getDescription())
                    .subscribe(RxTextView.text(holder.newsSummaryDigestTv));
                Observable.just(MyUtils.TimeStamp2Date(data.getTime()+""))
                    .subscribe(RxTextView.text(holder.newsSummaryPtimeTv));
                FrescoHelper.displayImageResize(holder.newsSummaryPhotoIv,"http://tnfs.tngou.net/img"+data.getImg(),150,150,1);

            }
        }
        return convertView;

    }

    class ViewHolder {
        @BindView(R.id.news_summary_photo_iv)
        SimpleDraweeView newsSummaryPhotoIv;
        @BindView(R.id.news_summary_title_tv)
        TextView newsSummaryTitleTv;
        @BindView(R.id.news_summary_digest_tv)
        TextView newsSummaryDigestTv;
        @BindView(R.id.news_summary_ptime_tv)
        TextView newsSummaryPtimeTv;
        public ViewHolder(View view) {
            KnifeUtil.bindTarget(this, view);//绑定
        }
    }

}
