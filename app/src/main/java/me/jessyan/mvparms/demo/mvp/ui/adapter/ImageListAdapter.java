package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.utils.KnifeUtil;
import io.reactivex.Observable;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.FrescoHelper;
import me.jessyan.mvparms.demo.base.BaseListAdapter;
import me.jessyan.mvparms.demo.mvp.model.entity.TngouBean;

/**
 * Created by xing on 2016/12/1.
 */
public class ImageListAdapter extends BaseListAdapter {

    private Context context;

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            context = parent.getContext();
            convertView = getLayoutInflater(context).inflate(R.layout.recycle_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (dataList != null && dataList.size() > 0) {

            if (dataList.get(position) instanceof TngouBean) {
               TngouBean entity = (TngouBean) dataList.get(position);
                Observable.just(entity.getTitle())
                    .subscribe(RxTextView.text(holder.mName));
                FrescoHelper.displayImageResize(holder.ivAvatar,"http://tnfs.tngou.net/img"+entity.getImg(),300,150,2);

            }
        }
            return convertView;

    }

    class ViewHolder {
        @Nullable
        @BindView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;

        @Nullable
        @BindView(R.id.tv_name)
        TextView mName;
        public ViewHolder(View view) {
            KnifeUtil.bindTarget(this, view);//绑定
        }
    }


}
