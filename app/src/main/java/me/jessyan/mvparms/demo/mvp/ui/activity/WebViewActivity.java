package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.BasePresenter;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.base.BaseRefreshActivity;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by xing on 2016/12/13.
 */

public class WebViewActivity extends BaseRefreshActivity<BasePresenter> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.titleTV)
    TextView titleTV;
    ProgressBar webLoadPB;
    private String title;
    private String url;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void loadData() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        webLoadPB = (ProgressBar) findViewById(R.id.webLoadPB);
        titleTV.setText(title);
        webView.loadUrl(url);
    }

    @Override
    protected void setListener() {
        super.setListener();
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webLoadPB.setVisibility(View.VISIBLE);
                webLoadPB.setProgress(newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webLoadPB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webLoadPB.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    protected void ComponentInject() {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }


    @Override
    protected void finishActivity() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.finishActivity();
        }
    }

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
        super.onDestroy();
    }
}