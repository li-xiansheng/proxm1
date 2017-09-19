package com.muzikun.lhfsyczl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.TabFragmentAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.fragment.lotoking.LotoKingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：六合王
 */

public class LotoKingActivity extends BaseActivity {

    @BindView(R.id.radioGroup)
    public RadioGroup radioGroup;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private List<BaseFragment> fragments;

    @Override
    protected void initBase(Bundle savedInstanceState) {
        isShowToolBar = false;
        super.initBase(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_loto_king;
    }


    @Override
    protected void initView() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            String[] titles = {"单双", "大小", "生肖", "号码"};
            fragments = new ArrayList<>();


            for (int i = 0; i < titles.length; i++) {
                LotoKingFragment fragment = new LotoKingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("text", titles[i]);
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), mContext));
            tabLayout.setTabMode(tabLayout.MODE_FIXED);
            // 将ViewPager和TabLayout绑定
            tabLayout.setupWithViewPager(viewPager);
            // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
            tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LotoKingFragment fragment = ((LotoKingFragment) fragments.get(viewPager.getCurrentItem()));
                if (fragment.mList != null) {
                    fragment.mList.clear();
                }
                //用刷新的UI去触发下拉刷新
                fragment.mPullToRefreshRecyclerView.setRefreshing(true);
//                fragment.getData();
            }
        });
    }

    @Override
    protected void initData() {

    }


}
