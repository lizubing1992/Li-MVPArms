package me.jessyan.mvparms.demo.mvp.ui.fragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.BindView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.component.AppComponent;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import java.util.ArrayList;
import java.util.List;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.base.BaseListFragment;
import me.jessyan.mvparms.demo.di.component.DaggerImageListComponent;
import me.jessyan.mvparms.demo.di.component.DaggerNewsListComponent;
import me.jessyan.mvparms.demo.di.module.ImageListModule;
import me.jessyan.mvparms.demo.di.module.NewsListModule;
import me.jessyan.mvparms.demo.mvp.contract.NewsListContract;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity.TngouBean;
import me.jessyan.mvparms.demo.mvp.presenter.NewsListPresenter;
import me.jessyan.mvparms.demo.mvp.ui.activity.NewsDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.NewsListAdapter;
import me.jessyan.mvparms.demo.widget.EmptyLayout;
import timber.log.Timber;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by xing on 2016/12/7.
 */

public class NewsListFragment extends BaseListFragment<NewsListPresenter> implements
    NewsListContract.View{


    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    protected int id = 1;
    protected String cacheName = "";

    public static NewsListFragment newInstance(int id,String cacheName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("cacheName", cacheName);
        fragment.setArguments(bundle);
        return fragment;
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
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsListComponent
            .builder()
            .appComponent(appComponent)
            .newsListModule(new NewsListModule(this))//请将DaggerBaseListModule()第一个首字母改为小写
            .build()
            .inject(this);
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
    }

    @Override
    public void hideLoading() {
        refreshFinish();
        Timber.tag(TAG).w("hideLoading");
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        WEApplication.showToast(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

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
        mListAdapter = new NewsListAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsListEntity.TngouBean entity = (NewsListEntity.TngouBean) mListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("newsId", entity.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_user,null,false);
        return rootView;
    }

    @Override
    public void loadListData(List<TngouBean> mList, boolean isSuccess) {
        requestListFinish(isSuccess,mList);
    }

    @Override
    protected void requestList(boolean isCache) {
        mPresenter.requestList(cacheName, id, isCache);
    }
}