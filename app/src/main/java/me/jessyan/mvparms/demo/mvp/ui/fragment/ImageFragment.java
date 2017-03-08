package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.AppComponent;
import me.jessyan.mvparms.demo.mvp.ui.common.WEFragment;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by xing on 2016/12/6.
 */

public class ImageFragment extends WEFragment {

    private static final String[] CHANNELS = new String[]{"性感美女", "韩日美女", "丝袜美腿", "美女照片", "美女写真", "清纯美女", "性感车模"};
    @BindView(R.id.magic_indicator1)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private CommonNavigator mCommonNavigator;
    private List<String> mDataList = Arrays.asList(CHANNELS);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void ComponentInject() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void loadData() {
        AdapterViewPager adapterViewPager = new AdapterViewPager(getChildFragmentManager());
        adapterViewPager.bindData(initFragments(), CHANNELS);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(CHANNELS.length);
        initMagicIndicator();
    }

    private List<BaseFragment> initFragments() {
        List<BaseFragment> baseFragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            baseFragments.add(ImageListFragment.newInstance(i+1,CHANNELS[i]));
        }
        return baseFragments;
    }

    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        mCommonNavigator = new CommonNavigator(getActivity());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, viewPager);
    }

}