package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.ImageListContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ImageEntity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ImageListAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by xing on 2016/12/1.
 */

@ActivityScope
public class ImageListPresenter extends BasePresenter<ImageListContract.Model, ImageListContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private RxPermissions mRxPermissions;
    private List<ImageEntity.TngouBean> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;

    @Inject
    public ImageListPresenter(ImageListContract.Model model, ImageListContract.View rootView
            , RxErrorHandler handler, Application application, RxPermissions rxPermissions
            ) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mRxPermissions = rxPermissions;
        mAdapter = new ImageListAdapter(mUsers);
        mRootView.setAdapter(mAdapter);//设置Adapter
    }
    public void requestImageList(final String cacheName,final int id,final boolean pullToRefresh) {

        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }
        }, mRxPermissions, mRootView, mErrorHandler);


        if (pullToRefresh) page = 1;

        mModel.getImageList(cacheName,page, id, pullToRefresh)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh)
                            mRootView.showLoading();//显示上拉刷新的进度条
                        else
                            mRootView.startLoadMore();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh)
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        else
                            mRootView.endLoadMore();
                    }
                })
                .compose(((BaseFragment) mRootView).<ImageEntity>bindToLifecycle())//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<ImageEntity>(mErrorHandler) {
                    @Override
                    public void onNext(ImageEntity entity) {
                        page++;
                        if (pullToRefresh) mUsers.clear();//如果是上拉刷新则清空列表
                        for (ImageEntity.TngouBean bean : entity.getTngou()) {
                            mUsers.add(bean);
                        }
                        mAdapter.notifyDataSetChanged();//通知更新数据
                    }
                });
    }

}