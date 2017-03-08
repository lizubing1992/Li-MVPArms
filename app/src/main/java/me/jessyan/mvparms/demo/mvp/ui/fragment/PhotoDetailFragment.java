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
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            iamgeUrl = bundle.getString("imageUrl");
        }
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


    @Override
    protected int getLayoutId() {
        return 0;
    }
}