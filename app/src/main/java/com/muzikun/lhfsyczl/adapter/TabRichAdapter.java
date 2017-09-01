package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.design.widget.TabItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muzikun.lhfsyczl.base.BaseFragment;

import java.util.List;

/**
 * 功能描述：论坛社区，滑动式导航适配器
 */

public class TabRichAdapter extends FragmentPagerAdapter {
    private List<TabItem> tabItems;
    private Context context;
    private List<BaseFragment> fragments;

    public TabRichAdapter(List<BaseFragment> fragments, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }


    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
