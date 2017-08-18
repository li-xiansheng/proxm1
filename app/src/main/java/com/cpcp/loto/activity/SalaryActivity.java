package com.cpcp.loto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabRichAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.xinshui.CurrentFragment;
import com.cpcp.loto.fragment.xinshui.HistoryFragment;
import com.cpcp.loto.uihelper.GlideCircleTransform;
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

    String nickname;
    String avatar;
    String mobile;

    CurrentFragment currentFragment;
    HistoryFragment historyFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_salary;
    }

    @Override
    protected void initView() {
        setTitle("我的心水");

        Log.i(TAG, "mobile = " + mobile);
        salaryName.setText(nickname);
        if (!"".equals(avatar)) {
            Glide.with(this)
                    .load(avatar)
                    .transform(new GlideCircleTransform(mContext))
                    .into(salaryHead);
        } else {
            salaryHead.setImageResource(R.drawable.icon_default_head);
        }

        String[] mTitles = {"本期推荐", "历史推荐"};
        salaryTablayout.setTabData(mTitles);

        List<BaseFragment> fragments = new ArrayList<>();

       currentFragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        currentFragment.setArguments(bundle);
        fragments.add(currentFragment);

        historyFragment = new HistoryFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("mobile", mobile);
        historyFragment.setArguments(bundle2);
        fragments.add(historyFragment);

        salaryViewpager.setAdapter(new TabRichAdapter(fragments, getSupportFragmentManager(), this));
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

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            nickname = bundle.getString("nickname");
            avatar = bundle.getString("avatar");
            mobile = bundle.getString("mobile");
        }
    }
}
