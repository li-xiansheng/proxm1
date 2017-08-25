package com.cpcp.loto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cpcp.loto.MainActivity;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.HomeGridRecyclerAdapter;
import com.cpcp.loto.adapter.InfoStatisticsGridRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.fragment.statistics.StatisticsChildFragment;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.uihelper.PopMenuHelper;
import com.cpcp.loto.uihelper.ScrollGridLayoutManager;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.DividerItemDecorationGridHome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import butterknife.BindView;

/**
 * 功能描述：资讯统计（六合统计）
 */

public class InformationStatisticsActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private int currentPage = 0;

    private int[] resId;
    private String[] strIndex;
    private String[] url;
    PopupWindow mWindow;

    //各个Fragment操作页面
    public Fragment currentFragment;
    private StatisticsChildFragment fragment0;
    private StatisticsChildFragment fragment1;
    private StatisticsChildFragment fragment2;
    private StatisticsChildFragment fragment3;
    private StatisticsChildFragment fragment4;
    private StatisticsChildFragment fragment5;
    private StatisticsChildFragment fragment6;
    private StatisticsChildFragment fragment7;
    private StatisticsChildFragment fragment8;
    private StatisticsChildFragment fragment9;
    private StatisticsChildFragment fragment10;
    private StatisticsChildFragment fragment11;
    private StatisticsChildFragment fragment12;
    private StatisticsChildFragment fragment13;
    private StatisticsChildFragment fragment14;
    private StatisticsChildFragment fragment15;
    private StatisticsChildFragment fragment16;
    //
    private InfoStatisticsGridRecyclerAdapter infoStatisticsGridRecyclerAdapter;
    private List<Map<String, Object>> data;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_information_statistics;
    }

    @Override
    protected void initView() {
        setTitle("六合统计");
        setTopRightButton("期数:100", new OnMenuClickListener() {
            @Override
            public void onClick() {

            }
        });

        //
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);


        /*** the setting for BottomNavigationBar ***/
        mBottomNavigationBar.
                addItem(new BottomNavigationItem(R.drawable.item_tong_checked, "六合统计").
                        setInactiveIconResource(R.drawable.item_tong_normal).setActiveColorResource(R.color.redTextTab).
                        setInActiveColorResource(R.color.grayText))
                .addItem(new BottomNavigationItem(R.drawable.item_shuxing_checked, "属性参照").
                        setInactiveIconResource(R.drawable.item_shuxing_normal).setActiveColorResource(R.color.redTextTab).
                        setInActiveColorResource(R.color.grayText))
                .addItem(new BottomNavigationItem(R.drawable.item_te_checked, "特码历史").
                        setInactiveIconResource(R.drawable.item_te_normal).setActiveColorResource(R.color.redTextTab).
                        setInActiveColorResource(R.color.grayText))
                .addItem(new BottomNavigationItem(R.drawable.item_zheng_checked, "正码历史").
                        setInactiveIconResource(R.drawable.item_zheng_normal).setActiveColorResource(R.color.redTextTab).
                        setInActiveColorResource(R.color.grayText))//.setBadgeItem(badgeItem)
                .addItem(new BottomNavigationItem(R.drawable.item_more_checked, "更多").
                        setInactiveIconResource(R.drawable.item_more_normal).setActiveColorResource(R.color.redTextTab).
                        setInActiveColorResource(R.color.grayText))
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        mBottomNavigationBar.selectTab(currentPage);

    }


    @Override
    protected void initData() {

        resId = new int[]{
                R.drawable.item_tong_normal,
                R.drawable.item_shuxing_normal,
                R.drawable.item_te_normal,
                R.drawable.item_zheng_normal,
                R.drawable.item_da_normal,
                R.drawable.item_sheng_normal,
                R.drawable.item_xiao_normal,
                R.drawable.item_bo_normal,
                R.drawable.item_se_normal,
                R.drawable.item_liang_normal,
                R.drawable.item_wei_normal,
                R.drawable.item_shu_normal,
                R.drawable.item_fen_normal,
                R.drawable.item_duan_normal,
                R.drawable.item_qin_normal,
                R.drawable.item_lianma_normal,
                R.drawable.item_lianxiao_normal,


        };

        strIndex = new String[]{
                "六合统计", "属性参照", "特码历史", "正码历史",
                "尾数大小", "生肖特码", "生肖正码", "波色特码",
                "波色正码", "特码两面", "特码尾数", "正码尾数",
                "正码总分", "号码波段", "家禽野兽", "连码走势",
                "连肖走势"};

        // 待定跳转的class
        url = new String[]{
                HttpService.lotoStatistics,
                HttpService.attributeReference,
                HttpService.temaHistory,
                HttpService.orthocodeHistory,
                HttpService.tailSize,
                HttpService.animalTema,
                HttpService.animalOrthocode,
                HttpService.boseTema,
                HttpService.boseOrthocode,
                HttpService.TemaBothSides,
                HttpService.TemaMantissa,
                HttpService.OrthocodeMantissa,
                HttpService.OrthocodeTotal,
                HttpService.numberBand,
                HttpService.animal,
                HttpService.jointMark,
                HttpService.lianxiao,

        };
        data = new ArrayList<>();
        for (int i = 0; i < resId.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", strIndex[i]);
            map.put("img", resId[i]);
            map.put("url", url[i]);
            data.add(map);
        }
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 4) {
            showPopupWindow();
        } else {
            showWebPage(position);
        }
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        if (position == 4) {
            showPopupWindow();
            return;
        }
    }


    /**
     * 弹出筛选层
     */
    private void showPopupWindow() {
        mWindow = PopMenuHelper.newBasicPopupWindow(mActivity);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.popmenu_information_statistics, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initGridData(recyclerView);
        mWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        mWindow.setContentView(view);
        mWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, -(mBottomNavigationBar.getHeight()));
    }


    /**
     * 初始化运用模块数据
     */
    private void initGridData(RecyclerView recyclerView) {
        ScrollGridLayoutManager manager = new ScrollGridLayoutManager(mContext, 4);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecorationGridHome(mContext, 1, this.getResources().getColor(R.color.grayBgLight)));


        infoStatisticsGridRecyclerAdapter = new InfoStatisticsGridRecyclerAdapter(mActivity, data);

        String title = tvTitle.getText().toString();
        int position = Arrays.asList(strIndex).indexOf(title);

        infoStatisticsGridRecyclerAdapter.setSelectedPosition(position);

        recyclerView.setAdapter(infoStatisticsGridRecyclerAdapter);
        infoStatisticsGridRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mWindow != null && !mActivity.isFinishing()) {
                    mWindow.dismiss();
                    mWindow = null;
                }
                if (position < 4) {
                    mBottomNavigationBar.selectTab(position);
                } else {
                    showWebPage(position);
                }

            }
        });
    }

    /**
     * 显示替换对应的webView页面
     *
     * @param position
     */
    private void showWebPage(int position) {
        if (data == null || url == null || strIndex == null) {
            initData();
        }
        String name = (String) data.get(position).get("name");
        String url = (String) data.get(position).get("url");

        setTitle(name);


        switch (position) {
            case 0:
                if (fragment0 == null) {
                    fragment0 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment0, R.id.sub_content, name);
                currentFragment = fragment0;

                break;

            case 1:
                if (fragment1 == null) {
                    fragment1 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment1, R.id.sub_content, name);
                currentFragment = fragment1;
                break;
            case 2:
                if (fragment2 == null) {

                    fragment2 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment2, R.id.sub_content, name);
                currentFragment = fragment2;
                break;
            case 3:
                if (fragment3 == null) {
                    fragment3 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment3, R.id.sub_content, name);
                currentFragment = fragment3;
                break;
            case 4:
                if (fragment4 == null) {
                    fragment4 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment4, R.id.sub_content, name);
                currentFragment = fragment4;
                break;
            case 5:
                if (fragment5 == null) {
                    fragment5 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment5, R.id.sub_content, name);
                currentFragment = fragment5;
                break;
            case 6:
                if (fragment6 == null) {
                    fragment6 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment6, R.id.sub_content, name);
                currentFragment = fragment6;
                break;
            case 7:
                if (fragment7 == null) {
                    fragment7 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment7, R.id.sub_content, name);
                currentFragment = fragment7;
                break;
            case 8:
                if (fragment8 == null) {
                    fragment8 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment8, R.id.sub_content, name);
                currentFragment = fragment8;
                break;
            case 9:
                if (fragment9 == null) {
                    fragment9 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment9, R.id.sub_content, name);
                currentFragment = fragment9;
                break;
            case 10:
                if (fragment10 == null) {
                    fragment10 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment10, R.id.sub_content, name);
                currentFragment = fragment10;
                break;
            case 11:
                if (fragment11 == null) {
                    fragment11 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment11, R.id.sub_content, name);
                currentFragment = fragment11;
                break;
            case 12:
                if (fragment12 == null) {
                    fragment12 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment12, R.id.sub_content, name);
                currentFragment = fragment12;
                break;
            case 13:
                if (fragment13 == null) {
                    fragment13 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment13, R.id.sub_content, name);
                currentFragment = fragment13;
                break;
            case 14:
                if (fragment14 == null) {
                    fragment14 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment14, R.id.sub_content, name);
                currentFragment = fragment14;
                break;
            case 15:
                if (fragment15 == null) {
                    fragment15 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment15, R.id.sub_content, name);
                currentFragment = fragment15;
                break;
            case 16:
                if (fragment16 == null) {
                    fragment16 = StatisticsChildFragment.newInstance(name, url);
                }
                jumpFragment(currentFragment, fragment16, R.id.sub_content, name);
                currentFragment = fragment16;
                break;
        }
    }
}
