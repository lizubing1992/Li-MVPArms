package me.jessyan.mvparms.demo.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.DateUtil;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.FrescoHelper;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.utils.MyUtils;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by xing on 2016/12/1.
 */
public class NewsListItemHolder extends BaseHolder<NewsListEntity.TngouBean> {
    @BindView(R.id.news_summary_photo_iv)
    SimpleDraweeView newsSummaryPhotoIv;
    @BindView(R.id.news_summary_title_tv)
    TextView newsSummaryTitleTv;
    @BindView(R.id.news_summary_digest_tv)
    TextView newsSummaryDigestTv;
    @BindView(R.id.news_summary_ptime_tv)
    TextView newsSummaryPtimeTv;

    private final WEApplication mApplication;
    public NewsListItemHolder(View v) {
        super(v);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
    }
    @Override
    public void setData(NewsListEntity.TngouBean data) {
        Observable.just(data.getTitle())
                .subscribe(RxTextView.text(newsSummaryTitleTv));
        Observable.just(data.getDescription())
                .subscribe(RxTextView.text(newsSummaryDigestTv));
        Observable.just(MyUtils.TimeStamp2Date(data.getTime()+""))
                .subscribe(RxTextView.text(newsSummaryPtimeTv));
        FrescoHelper.displayImageResize(newsSummaryPhotoIv,"http://tnfs.tngou.net/img"+data.getImg(),150,150,1);

    }

}
