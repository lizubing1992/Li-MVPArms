package me.jessyan.mvparms.demo.mvp.ui.fragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.mvp.model.entity.TngouBean;
import me.jessyan.mvparms.demo.mvp.ui.activity.ImageDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageListAdapter;
import timber.log.Timber;
public class ImageListFragment extends DaggerBaseListFragment{
    public static ImageListFragment newInstance(int id,String cacheName) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("cacheName", cacheName);
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void loadListData(String  list,boolean isSuccess) {
        ArrayList<TngouBean> mList = new Gson().fromJson(list,
            new TypeToken<ArrayList<TngouBean>>() {
            }.getType());
        requestListFinish(isSuccess,mList);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        Bundle bundle = getArguments();
        if(null != bundle) {
            id = bundle.getInt("id", 1);
            cacheName = bundle.getString("cacheName");
            url = "/tnfs/api/list";
        }
        mListAdapter = new ImageListAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TngouBean entity = (TngouBean) mListAdapter.getItem(i);
                Intent intent =  new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("imageDetailId",entity.getId());
                startActivity(intent);
            }
        });
    }

    private boolean hasLoadOnce = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !hasLoadOnce) {
            if(id != 1) {
                requestList(true);
            }
            hasLoadOnce = true;
        }
    }

    @Override
    protected void loadData() {
        if(id == 1) {
            requestList(true);
        }
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
    }

    @Override
    public void hideLoading() {
        refreshFinish();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        WEApplication.showToast(message);
    }

}