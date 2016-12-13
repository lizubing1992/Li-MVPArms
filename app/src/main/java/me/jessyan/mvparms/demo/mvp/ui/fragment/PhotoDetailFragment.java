package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.mvp.ui.common.WEFragment;
import me.jessyan.mvparms.demo.widget.CircularProgressBar;
import photoview.ImageDownloadListener;
import photoview.PhotoView;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by xing on 2016/12/5.
 */

public class PhotoDetailFragment extends WEFragment {


    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.loading)
    CircularProgressBar loading;
    @BindView(R.id.level)
    TextView level;
    private String iamgeUrl;

    public static PhotoDetailFragment newInstance() {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    protected View initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            iamgeUrl = bundle.getString("imageUrl");
        }
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_photo_detail, null, false);
    }

    @Override
    protected void initData() {
        level.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        initPhotoView();
    }

    private void initPhotoView() {
        photoView.setImageUri("http://tnfs.tngou.net/img" + iamgeUrl);
        photoView.setImageDownloadListener(new ImageDownloadListener() {
            @Override
            public void onUpdate(int progress) {
                level.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                level.setText(progress + "%");
                loading.setProgress(progress);
                if (progress == 100) {
                    level.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     * <p/>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}