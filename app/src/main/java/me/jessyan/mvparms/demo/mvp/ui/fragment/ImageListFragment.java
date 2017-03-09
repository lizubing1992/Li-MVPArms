package me.jessyan.mvparms.demo.mvp.ui.fragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import java.util.List;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.app.WEApplication;
import me.jessyan.mvparms.demo.base.BaseListFragment;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.di.component.DaggerImageListComponent;
import me.jessyan.mvparms.demo.di.module.ImageListModule;
import me.jessyan.mvparms.demo.mvp.contract.ImageListContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.presenter.ImageListPresenter;
import me.jessyan.mvparms.demo.mvp.ui.activity.ImageDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageListAdapter;
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
 * Created by xing on 2016/12/1.
 */

public class ImageListFragment extends BaseListFragment<ImageListPresenter> implements
        ImageListContract.View{

    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;
    private int id = 1;
    private String cacheName = "";

    public static ImageListFragment newInstance(int id,String cacheName) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("cacheName", cacheName);
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerImageListComponent
                .builder()
                .appComponent(appComponent)
                .imageListModule(new ImageListModule(this))//请将ImageListModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected void requestList(boolean isCache) {
       mPresenter.requestImageList(cacheName, id, isCache);
    }


    @Override
    protected void loadData() {
        super.loadData();
        if(id == 1) {
            mPresenter.requestImageList(cacheName, id, true);
        }
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
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
    }

    @Override
    protected void initView(View rootView) {
        initRecycleView();
        mListAdapter = new ImageListAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageEntity.TngouBean entity = (ImageEntity.TngouBean) mListAdapter.getItem(i);
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
                mPresenter.requestImageList(cacheName, id, true);
            }
            hasLoadOnce = true;
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

    @Override
    public void loadData(List list,boolean isSuccess) {
        requestListFinish(isSuccess,list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }
}