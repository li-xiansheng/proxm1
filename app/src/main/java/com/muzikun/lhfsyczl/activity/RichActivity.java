package com.muzikun.lhfsyczl.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.TabRichAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.fragment.rich.CaiShenFragment;
import com.muzikun.lhfsyczl.fragment.rich.IntroduceFragment;
import com.muzikun.lhfsyczl.fragment.rich.InviteCodeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：致富
 */

public class RichActivity extends BaseActivity {
    @BindView(R.id.rich_tablayout)
    TabLayout richTablayout;
    @BindView(R.id.rich_viewpager)
    ViewPager richViewpager;
//    @BindView(R.id.rich_tabitem1)
//    TabItem richTabitem1;
//    @BindView(R.id.rich_tabitem2)
//    TabItem richTabitem2;
//    @BindView(R.id.rich_tabitem3)
//    TabItem richTabitem3;

    List<BaseFragment> fragments = new ArrayList<>();
//    List<TabItem> tabItems = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rich;
    }

    @Override
    protected void initView() {
        setTitle("致富");

        String[] titles = {"分享邀请码", "我的财神", "专家介绍"};

        fragments.add(new InviteCodeFragment());
        fragments.add(new CaiShenFragment());
        fragments.add(new IntroduceFragment());

        richViewpager.setOffscreenPageLimit(2);
        richViewpager.setAdapter(new TabRichAdapter(fragments, getSupportFragmentManager(), this));
        richTablayout.setupWithViewPager(richViewpager, false);

        richTablayout.getTabAt(0).setCustomView(R.layout.item_rich_tab1);
        richTablayout.getTabAt(1).setCustomView(R.layout.item_rich_tab2);
        richTablayout.getTabAt(2).setCustomView(R.layout.item_rich_tab3);

        richTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                richViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {

    }

}
