package com.muzikun.one.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.one.data.bean.NewViewPagerBean;

import java.util.List;

/**
 * Created by leeking on 16/6/1.
 */
public class FragmentNewViewPagerAdapter extends FragmentPagerAdapter {

    private List<NewViewPagerBean> list = null;
    private String[] titles  =null;

    public FragmentNewViewPagerAdapter(FragmentManager fm, List<NewViewPagerBean> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public View getTabView(int position){

        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return this.titles[position];
    }
}
