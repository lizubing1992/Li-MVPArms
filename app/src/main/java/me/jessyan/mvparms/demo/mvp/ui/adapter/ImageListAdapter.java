package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.ui.holder.ImageListItemHolder;

/**
 * Created by xing on 2016/12/1.
 */
public class ImageListAdapter extends DefaultAdapter<ImageEntity.TngouBean> {

    public ImageListAdapter(List<ImageEntity.TngouBean> mUsers) {
        super(mUsers);
    }

    @Override
    public BaseHolder getHolder(View v) {
        return new ImageListItemHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycle_list;
    }


}
