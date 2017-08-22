package com.cpcp.loto.fragment.xinshui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class HistoryFragment extends BaseFragment {


    @BindView(R.id.tabLayout)
    TabLayout historyTablayout;
    @BindView(R.id.history_viewpager)
    ViewPager historyViewpager;

    List<BaseFragment> fragments = new ArrayList<>();

    String mobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView() {
        String[] mTitles = {
                "综合", "生肖", "号码"
                , "大小", "单双"};

        for (int i = 0; i < mTitles.length; i++) {
            HistoryItemFragment fragment = new HistoryItemFragment();
            Bundle bundle = new Bundle();
            Log.i(TAG, "mTitles[i] = " + mTitles[i]);
            bundle.putString("title", mTitles[i]);
            bundle.putString("mobile", mobile);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        historyViewpager.setOffscreenPageLimit(3);
        historyViewpager.setAdapter(new TabFragmentAdapter(fragments,mTitles,getFragmentManager(),mContext));

        // 将ViewPager和TabLayout绑定
        historyTablayout.setupWithViewPager(historyViewpager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
//        historyTablayout.setTabTextColors(Color.BLACK, Color.BLUE);
        historyTablayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.colorPrimary));

        //主动调取第一个页面可见执行懒加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragments.get(0).setUserVisibleHint(true);
            }
        }, 500);

    }

    @Override
    public void onLazyLoadData() {

    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getArguments();
        mobile = bundle.getString("mobile");
    }

}
