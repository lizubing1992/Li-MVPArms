package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.mvp.ui.holder.NewsListItemHolder;

/**
 * Created by xing on 2016/12/7.
 */
public class NewsListAdapter extends DefaultAdapter<NewsListEntity.TngouBean> {
    public NewsListAdapter(List<NewsListEntity.TngouBean> beanList) {
        super(beanList);
    }

    @Override
    public BaseHolder getHolder(View v) {
        return new NewsListItemHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_news_list;
    }

}
