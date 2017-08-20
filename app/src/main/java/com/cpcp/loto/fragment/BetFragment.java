package com.cpcp.loto.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.bet.BoSeBetFragment;
import com.cpcp.loto.fragment.bet.DaXiaoBetFragment;
import com.cpcp.loto.fragment.bet.DanShuangBetFragment;
import com.cpcp.loto.fragment.bet.ShengxiaoBetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：彩民投注
 */

public class BetFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    /**
     * 构造Fragment
     *
     * @return 当前Fragment对象
     */
    public static BetFragment newInstance() {
        BetFragment fragment = new BetFragment();
        return fragment;
    }


    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_bet;
    }

    @Override
    protected void initView() {
        String[] titles = {"波色投票", "大小投票", "单双投票", "生肖投票"};
        final List<BaseFragment> fragments = new ArrayList<>();

        BaseFragment fragment;
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                fragment = new BoSeBetFragment();
            } else if (i == 1) {
                fragment = new DaXiaoBetFragment();
            } else if (i == 2) {
                fragment = new DanShuangBetFragment();
            } else if (i == 3) {
                fragment = new ShengxiaoBetFragment();
            } else {
                break;
            }
            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, this.getActivity().getSupportFragmentManager(), mContext));

        // 将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tabLayout.setTabTextColors(Color.BLACK, R.color.colorPrimary);
        tabLayout.setTabMode(tabLayout.MODE_FIXED);
        //主动调取第一个页面可见执行懒加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragments.get(0).setUserVisibleHint(true);
            }
        }, 1000);
    }


    @Override
    public void onLazyLoadData() {

    }


}
