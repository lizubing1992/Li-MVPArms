package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerNewsListComponent;
import me.jessyan.mvparms.demo.di.module.NewsListModule;
import me.jessyan.mvparms.demo.mvp.contract.NewsListContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.mvp.presenter.NewsListPresenter;
import me.jessyan.mvparms.demo.mvp.ui.activity.ImageDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.activity.NewsDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.common.WEFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

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

public class NewsListFragment extends WEFragment<NewsListPresenter> implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private int id = 1;
    private String cacheName = "";


    public static NewsListFragment newInstance(int id,String cacheName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("cacheName", cacheName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsListComponent
                .builder()
                .appComponent(appComponent)
                .newsListModule(new NewsListModule(this))//请将NewsListModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        Bundle bundle = getArguments();
        if(null != bundle) {
            id = bundle.getInt("id", 1);
            cacheName = bundle.getString("cacheName");
        }
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_user, null, false);
    }


    private boolean hasLoadOnce = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !hasLoadOnce) {
            if(id != 1) {
                mPresenter.requestNewsList(cacheName, id, true);
            }
            hasLoadOnce = true;
        }
    }
    @Override
    public void onRefresh() {
        mPresenter.requestNewsList(cacheName, id, true);
    }
    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        configRecycleView(recyclerView, new LinearLayoutManager(getActivity()));
    }
    @Override
    protected void initData() {
        if(id == 1) {
            mPresenter.requestNewsList(cacheName, id, true);
        }
    }

    /**
     * 配置recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void configRecycleView(RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager
    ) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(getActivity(), intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setAdapter(final DefaultAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                NewsListEntity.TngouBean entity = (NewsListEntity.TngouBean) adapter.getInfos().get(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("newsId", entity.getId());
                startActivity(intent);
            }
        });
        initRecycleView();
        initPaginate();
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestNewsList(cacheName, id, false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(recyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }
}