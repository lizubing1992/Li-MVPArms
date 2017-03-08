package me.jessyan.mvparms.demo.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;

public class BasePagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private final ArrayList<String> mFragmentTitles = new ArrayList<>();

    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public String[] getTitles() {
        String[] titles = new String[mFragmentTitles.size()];
        for (int i = 0; i < mFragmentTitles.size(); i++) {
            titles[i] = mFragmentTitles.get(i);
        }
        return titles;
    }

    public ArrayList<Fragment> getmFragments() {
        return mFragments;
    }

}
