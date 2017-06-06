package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.BindView;
import com.jess.arms.di.component.AppComponent;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import java.util.List;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.base.BaseListFragment;
import me.jessyan.mvparms.demo.di.component.DaggerImageListComponent;
import me.jessyan.mvparms.demo.di.module.ImageListModule;
import me.jessyan.mvparms.demo.mvp.contract.ImageListContract;
import me.jessyan.mvparms.demo.mvp.model.entity.TngouBean;
import me.jessyan.mvparms.demo.mvp.presenter.ImageListPresenter;
import me.jessyan.mvparms.demo.mvp.ui.activity.ImageDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageListAdapter;
import me.jessyan.mvparms.demo.widget.EmptyLayout;
import timber.log.Timber;

public class ImageListFragment extends BaseListFragment<ImageListPresenter> implements
    ImageListContract.View{

    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    protected int id = 1;
    protected String cacheName = "";
    public static ImageListFragment newInstance(int id,String cacheName) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("cacheName", cacheName);
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerImageListComponent
            .builder()
            .appComponent(appComponent)
            .imageListModule(new ImageListModule(this))//请将DaggerBaseListModule()第一个首字母改为小写
            .build()
            .inject(this);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        Bundle bundle = getArguments();
        if(null != bundle) {
            id = bundle.getInt("id", 1);
            cacheName = bundle.getString("cacheName");
        }
        setPageSize(20);
        mListView = listView;
        mEmptyLayout = emptyLayout;
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        ((ListView)mListView).setDividerHeight(10);
        mListView.setOnScrollListener(mScrollListener);
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


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_user,null,false);
        return rootView;
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
    protected void requestList(boolean isCache) {
        mPresenter.requestList(cacheName, id, isCache);
    }

    @Override
    public void loadListData(List<TngouBean> mList, boolean isSuccess) {
        Timber.e("loadListData----------------------------------"+mList.size());
          requestListFinish(isSuccess,mList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}