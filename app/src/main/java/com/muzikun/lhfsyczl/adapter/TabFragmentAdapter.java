package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muzikun.lhfsyczl.base.BaseFragment;

import java.util.List;

/**
 * 功能描述：论坛社区，滑动式导航适配器
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private final String[] titles;
    private Context context;
    private List<BaseFragment> fragments;

    public TabFragmentAdapter(List<BaseFragment> fragments, String[] titles, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }


    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
