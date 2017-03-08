package me.jessyan.mvparms.demo.base;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.ArrayList;

public class BaseViewPageAdapter extends PagerAdapter {

    ArrayList<View> views = new ArrayList<>();

    public void setData(ArrayList<View> list){
        if(list != null){
            views = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (views != null)
            return views.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View view, int position, Object object)
    {
        //销毁item
        if (views != null && views.size() > 0) {
            ((ViewPager) view).removeView(views.get(position));
        }
    }

    @Override
    public Object instantiateItem(View view, int position)
    {
        //实例化item
        if (views != null && views.size() > 0) {
            ((ViewPager) view).addView(views.get(position), 0);
            return views.get(position);
        }
        return null;
    }

    public View getItem(int position){
        if(views != null && views.size() > 0){
            return views.get(position);
        }
        return null;
    }
}
