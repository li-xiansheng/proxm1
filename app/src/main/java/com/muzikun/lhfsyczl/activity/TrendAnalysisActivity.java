package com.muzikun.lhfsyczl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.TabFragmentAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.fragment.trend.BoSeTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.DanShuangTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.DuanWeiTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.HeadTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.ShengXiaoTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.TailTrendFragment;
import com.muzikun.lhfsyczl.fragment.trend.WuXingTrendFragment;
import com.muzikun.lhfsyczl.uihelper.PopupWindowHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

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
    private List<BaseFragment> fragments;

    private AppCompatTextView tvTemp;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trend_analysis;
    }

    @Override
    protected void initView() {
        setTitle("走势分析");
        tvTemp = new AppCompatTextView(mContext);

        setTopRightButton("年限查询", new OnMenuClickListener() {
            @Override
            public void onClick() {
                PopupWindowHelper.selectYear(mActivity, tvTemp);
            }
        });
        initFragment();
        //主动调取第一个页面可见执行懒加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fragments != null && fragments.size() > 0) {
                    fragments.get(0).setUserVisibleHint(true);
                } else {
                    initFragment();
                    fragments.get(0).setUserVisibleHint(true);
                }

            }
        }, 1000);
    }

    private void initFragment() {
        if (fragments == null || fragments.size() == 0) {
            String[] titles = {"生肖走势", "波色走势", "单双走势", "段位走势", "头数走势", "尾数走势", "五行走势"};
            fragments = new ArrayList<>();

            BaseFragment fragment;
            for (int i = 0; i < titles.length; i++) {
                if (i == 0) {
                    fragment = new ShengXiaoTrendFragment();
                } else if (i == 1) {
                    fragment = new BoSeTrendFragment();
                } else if (i == 2) {
                    fragment = new DanShuangTrendFragment();
                } else if (i == 3) {
                    fragment = new DuanWeiTrendFragment();
                } else if (i == 4) {
                    fragment = new HeadTrendFragment();
                } else if (i == 5) {
                    fragment = new TailTrendFragment();
                } else if (i == 6) {
                    fragment = new WuXingTrendFragment();
                } else {//无效
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
            tabLayout.setTabTextColors(Color.BLACK, mContext.getResources().getColor(R.color.colorPrimary));

        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        tvTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String values = tvTemp.getText().toString();
                if (!"走势分析".equals(values) && values != null) {
                    menuStr = values;
                    invalidateOptionsMenu();

                    BasePullRefreshFragment fragment = ((BasePullRefreshFragment) fragments.get(viewPager.getCurrentItem()));
                    //使用刷新UI触发下拉刷新加载
                    fragment.mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    fragment.mPullToRefreshRecyclerView.setRefreshing(true);
//                    fragment.onLazyLoadData();
                } else {

                }
            }
        });
    }
}
