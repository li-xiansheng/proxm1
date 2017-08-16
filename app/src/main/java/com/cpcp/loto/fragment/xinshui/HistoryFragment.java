package com.cpcp.loto.fragment.xinshui;

import android.support.v4.view.ViewPager;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class HistoryFragment extends BaseFragment {


    @BindView(R.id.history_tablayout)
    SlidingTabLayout historyTablayout;
    @BindView(R.id.history_viewpager)
    ViewPager historyViewpager;

    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView() {
        String[] mTitles = {
                "综合", "生肖", "号码"
                , "大小", "单双"};

        fragments.add(new HistoryItemFragment());
        fragments.add(new HistoryItemFragment());
        fragments.add(new HistoryItemFragment());
        fragments.add(new HistoryItemFragment());
        fragments.add(new HistoryItemFragment());

        historyViewpager.setAdapter(new TabFragmentAdapter(fragments,mTitles,getFragmentManager(),mContext));
        historyTablayout.setViewPager(historyViewpager);

    }

    @Override
    public void onLazyLoadData() {

    }

}
