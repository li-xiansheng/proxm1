package com.cpcp.loto.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabRichAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.xinshui.HistoryFragment;
import com.cpcp.loto.fragment.xinshui.currentFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：我的薪水
 */

public class SalaryActivity extends BaseActivity {

    @BindView(R.id.salary_head)
    AppCompatImageView salaryHead;
    @BindView(R.id.salary_name)
    TextView salaryName;
    @BindView(R.id.salary_tablayout)
    SegmentTabLayout salaryTablayout;
    @BindView(R.id.salary_viewpager)
    ViewPager salaryViewpager;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_salary;
    }

    @Override
    protected void initView() {
        setTitle("我的薪水");

        String[] mTitles = {"本期推荐", "历史推荐"};
        salaryTablayout.setTabData(mTitles);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new currentFragment());
        fragments.add(new HistoryFragment());

        salaryViewpager.setAdapter(new TabRichAdapter(fragments,getSupportFragmentManager(),this));
        salaryTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                salaryViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        salaryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                salaryTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

}
