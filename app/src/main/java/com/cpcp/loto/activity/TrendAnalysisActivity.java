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
import com.cpcp.loto.fragment.trend.BoSeTrendFragment;
import com.cpcp.loto.fragment.trend.DanShuangTrendFragment;
import com.cpcp.loto.fragment.trend.DuanWeiTrendFragment;
import com.cpcp.loto.fragment.trend.HeadTrendFragment;
import com.cpcp.loto.fragment.trend.ShengXiaoTrendFragment;
import com.cpcp.loto.fragment.trend.TailTrendFragment;
import com.cpcp.loto.fragment.trend.WuXingTrendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：走势分析
 */

public class TrendAnalysisActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trend_analysis;
    }

    @Override
    protected void initView() {
        setTitle("走势分析");
        setTopRightButton("年份查询", new OnMenuClickListener() {
            @Override
            public void onClick() {
                //TODO 默认当前年限，仍需处理筛选
            }
        });
        String[] titles = {"生肖走势", "波色走势","单双走势", "段位走势", "头数走势", "尾数走势", "五行走势"};
        final List<BaseFragment> fragments = new ArrayList<>();

        BaseFragment fragment;
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                fragment = new ShengXiaoTrendFragment();
            } else if (i == 1) {
                fragment = new BoSeTrendFragment();
            }else if (i == 2) {
                fragment = new DanShuangTrendFragment();
            }else if (i == 3) {
                fragment = new DuanWeiTrendFragment();
            }else if (i == 4) {
                fragment = new HeadTrendFragment();
            }else if (i == 5) {
                fragment = new TailTrendFragment();
            }else if (i == 6) {
                fragment = new WuXingTrendFragment();
            }else{//无效
                break;
            }
            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), mContext));

        // 将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tabLayout.setTabTextColors(Color.BLACK, R.color.colorPrimary);
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
