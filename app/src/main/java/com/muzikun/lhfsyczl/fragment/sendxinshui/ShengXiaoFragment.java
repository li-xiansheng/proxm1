package com.muzikun.lhfsyczl.fragment.sendxinshui;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.XinShuiGridRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.uihelper.ScrollGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述:发布心水，生肖
 */

public class ShengXiaoFragment extends BaseFragment {
    @BindView(R.id.etTitle)
    public AppCompatEditText etTitle;
    @BindView(R.id.etScore)
    public AppCompatEditText etScore;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private XinShuiGridRecyclerAdapter xinShuiRecyclerAdapter;
    public List<Map<String, Object>> data;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shend_shengxiao;
    }

    @Override
    protected void initView() {
        initGrid();
    }


    @Override
    public void onLazyLoadData() {

    }

    /**
     * 初始化网格数据
     */
    private void initGrid() {
        ScrollGridLayoutManager manager = new ScrollGridLayoutManager(mContext, 4);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);

        data = new ArrayList<>();
        String[] strIndex = new String[]{
                "鼠", "牛", "虎", "兔",
                "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪"};

        for (int i = 0; i < strIndex.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", strIndex[i]);
            map.put("isChecked", false);
            data.add(map);
        }
        xinShuiRecyclerAdapter = new XinShuiGridRecyclerAdapter(mActivity, data);
        xinShuiRecyclerAdapter.setMaxChecked(5);
        recyclerView.setAdapter(xinShuiRecyclerAdapter);
    }
}
