package com.muzikun.one.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by likun on 16/6/22.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class FragmentPeopleLifeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = null;

    public FragmentPeopleLifeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] titles = new String[]{
                "风水讲座","经典视频",  "爆料"
        };
        return titles[position];
    }
}
