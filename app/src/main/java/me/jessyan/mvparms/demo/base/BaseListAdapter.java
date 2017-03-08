package me.jessyan.mvparms.demo.base;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.base.Base2Adapter;
import me.jessyan.mvparms.demo.R;


public class BaseListAdapter extends Base2Adapter {

    protected int listState = LIST_STATE_DEFAULT;

    public void setListState(int listState) {
        this.listState = listState;
        notifyDataSetChanged();
    }

    public int getListState() {
        return listState;
    }

    @Override
    public int getCount() {
        switch (getListState()) {
            case LIST_STATE_ERROR:
            case LIST_STATE_LOAD_MORE:
            case LIST_STATE_NO_MORE:
            case LIST_STATE_EMPTY:
                return getDataSize() + 1;
            case LIST_STATE_DEFAULT:
            default:
                return getDataSize();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1) {// 最后一条
            if (getListState() == LIST_STATE_LOAD_MORE
                    || getListState() == LIST_STATE_NO_MORE
                    || getListState() == LIST_STATE_EMPTY
                    || getListState() == LIST_STATE_ERROR) {

                View footView =  getLayoutInflater(parent.getContext()).inflate(
                        R.layout.layout_load_more_footer, null);

                TextView loadingTV = (TextView)footView.findViewById(R.id.loadingTV);
                TextView noMoreTV = (TextView)footView.findViewById(R.id.noMoreTV);
                LinearLayout loadingLL = (LinearLayout)footView.findViewById(R.id.loadingLL);

                footView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                switch (getListState()) {
                    case LIST_STATE_LOAD_MORE:
                        footView.setVisibility(View.VISIBLE);
                        loadingLL.setVisibility(View.VISIBLE);
                        loadingTV.setText(R.string.loading);
                        noMoreTV.setVisibility(View.INVISIBLE);
                        break;
                    case LIST_STATE_NO_MORE:
                        footView.setVisibility(View.VISIBLE);
                        loadingLL.setVisibility(View.INVISIBLE);
                        noMoreTV.setVisibility(View.VISIBLE);
                        noMoreTV.setText(R.string.loading_no_more);
                        break;
                    case LIST_STATE_ERROR:
                        footView.setVisibility(View.VISIBLE);
                        loadingLL.setVisibility(View.INVISIBLE);
                        noMoreTV.setVisibility(View.VISIBLE);
                        noMoreTV.setText(R.string.loading_net_error);
                        break;
                    case LIST_STATE_EMPTY:
                    default:
                        footView.setVisibility(View.GONE);
                        loadingLL.setVisibility(View.GONE);
                        noMoreTV.setVisibility(View.GONE);
                        break;
                }

                return footView;
            }
        }
        return getRealView(position, convertView, parent);
    }


}
