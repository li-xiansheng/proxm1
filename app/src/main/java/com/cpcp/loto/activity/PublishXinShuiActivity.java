package com.cpcp.loto.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.publish.DaxiaoFragment;
import com.cpcp.loto.fragment.publish.HaomaFragment;
import com.cpcp.loto.fragment.publish.ShengXiaoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublishXinShuiActivity extends BaseActivity {


    List<BaseFragment> fragments = new ArrayList<>();
    @BindView(R.id.publish_tablayout)
    TabLayout publishTablayout;
    @BindView(R.id.publish_viewpager)
    ViewPager publishViewpager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_xin_shui;
    }

    @Override
    protected void initView() {

        setTitle("发布心水");
        setTopRightButton("发布", new OnMenuClickListener() {
            @Override
            public void onClick() {

            }
        });

        String[] titles = {"大小", "单双", "生肖", "号码"};

        for (int i = 0; i < 2; i++) {
            DaxiaoFragment fragment = new DaxiaoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        fragments.add(new ShengXiaoFragment());
        fragments.add(new HaomaFragment());

        publishViewpager.setAdapter(new TabFragmentAdapter(fragments, titles,getSupportFragmentManager(), this));
//        richTablayout.setupWithViewPager(richViewpager);
        publishTablayout.setupWithViewPager(publishViewpager, false);

    }

    @Override
    protected void initData() {

    }
}
