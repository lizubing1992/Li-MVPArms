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
import me.jessyan.mvparms.demo.mvp.model.entity.NewsListEntity;
import me.jessyan.mvparms.demo.mvp.ui.activity.NewsDetailActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.NewsListAdapter;
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

public class NewsListFragment extends DaggerBaseListFragment{

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
    protected void initView(View rootView) {
        super.initView(rootView);
        Bundle bundle = getArguments();
        if(null != bundle) {
            id = bundle.getInt("id", 1);
            cacheName = bundle.getString("cacheName");
            url = "/api/top/list";
        }
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
    public void loadListData(String  list,boolean isSuccess) {
        ArrayList<NewsListEntity.TngouBean> mList = new Gson().fromJson(list,
            new TypeToken<ArrayList<NewsListEntity.TngouBean>>() {
            }.getType());
        requestListFinish(true,mList);
    }
}