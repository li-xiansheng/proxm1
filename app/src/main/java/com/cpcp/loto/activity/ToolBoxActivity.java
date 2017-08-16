package com.cpcp.loto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cpcp.loto.MainActivity;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.HomeGridRecyclerAdapter;
import com.cpcp.loto.adapter.ToolBoxGridRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.uihelper.ScrollGridLayoutManager;
import com.cpcp.loto.view.DividerItemDecorationGridHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：工具宝箱
 */

public class ToolBoxActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private ToolBoxGridRecyclerAdapter toolBoxGridRecyclerAdapter;
    private List<Map<String, Object>> data;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tool_box;
    }

    @Override
    protected void initView() {
        setTitle("六合宝箱");
        initGridData();
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化运用模块数据
     */
    private void initGridData() {
        ScrollGridLayoutManager manager = new ScrollGridLayoutManager(mContext, 3);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecorationGridHome(mContext, 3, this.getResources().getColor(R.color.grayBgLight)));

        data = new ArrayList<>();

        int[] resId = new int[]{
                R.drawable.item_icon_shake,
                R.drawable.item_icon_shengxiao,
                R.drawable.item_icon_xuanji,
                R.drawable.item_icon_lover,
                R.drawable.item_icon_query,
                R.drawable.item_icon_turntable,
                R.drawable.item_icon_date,
                R.drawable.item_icon_tianji,
                R.drawable.item_icon_luck,


        };

        String[] strIndex = new String[]{
                "摇一摇", "生肖卡牌", "玄机锦囊",
                "恋人特码", "查询助手", "波肖转盘",
                "搅珠日期", "天机测算", "幸运摇奖"};

        // 待定跳转的class
        Class[] uri = new Class[]{
                ShakeActivity.class,
                ShengXiaoActivity.class,
                XuanJiActivity.class,
                LoverActivity.class,
                WebCommonPageActivity.class,
                TurntableActivity.class,
                JiaoZhuDateActivity.class,
                TianJiActivity.class,
                LuckActivity.class,

        };

        for (int i = 0; i < resId.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", strIndex[i]);
            map.put("img", resId[i]);
            map.put("uri", uri[i]);
            data.add(map);
        }

        toolBoxGridRecyclerAdapter = new ToolBoxGridRecyclerAdapter(mActivity, data);
        recyclerView.setAdapter(toolBoxGridRecyclerAdapter);
        toolBoxGridRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Class aClass = (Class<?>) data.get(position).get("uri");
                String name = (String) data.get(position).get("name");
                if ("查询助手".equals(name)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "查询助手");
                    bundle.putString("url", HttpService.queryHelper);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                } else {
                    Intent intent = new Intent((BaseActivity) mActivity, (Class<?>) data.get(position).get("uri"));
                    startActivity(intent);
                }


            }
        });
    }
}
