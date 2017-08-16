package com.cpcp.loto.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.bet.BetChildFragment;
import com.cpcp.loto.fragment.winning.WinningFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：高手资料---连胜榜
 */

public class WinningActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_winning;
    }

    @Override
    protected void initView() {
        setTitle("连胜榜");

        String[] titles = {"单双", "大小", "生肖", "号码"};
        final List<BaseFragment> fragments = new ArrayList<>();


        for (int i = 0; i < titles.length; i++) {
            WinningFragment fragment = new WinningFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), mContext));

        // 将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
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
    protected void initData() {

    }
}
