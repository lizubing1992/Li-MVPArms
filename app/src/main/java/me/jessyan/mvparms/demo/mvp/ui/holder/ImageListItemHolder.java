package me.jessyan.mvparms.demo.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.FrescoHelper;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.TngouBean;
import rx.Observable;

/**
 * Created by xing on 2016/12/1.
 */
public class ImageListItemHolder extends BaseHolder<TngouBean> {
    @Nullable
    @BindView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;

    @Nullable
    @BindView(R.id.tv_name)
    TextView mName;

    private final WEApplication mApplication;
    public ImageListItemHolder(View v) {
        super(v);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
    }
    @Override
    public void setData(TngouBean data) {
        Observable.just(data.getTitle())
                .subscribe(RxTextView.text(mName));
        FrescoHelper.displayImageResize(ivAvatar,"http://tnfs.tngou.net/img"+data.getImg(),300,150,2);
    }

}
