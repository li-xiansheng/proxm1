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
 * 功能描述:发布心水，号码
 */

public class HaoMaFragment extends BaseFragment {
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
        return R.layout.fragment_shend_haoma;
    }

    @Override
    protected void initView() {
        initGrid();
    }

    private void initGrid() {
        ScrollGridLayoutManager manager = new ScrollGridLayoutManager(mContext, 4);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);

        data = new ArrayList<>();
        List<String> haoma = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            haoma.add(String.format("%02d", i));
        }

        for (int i = 0; i < haoma.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", haoma.get(i));
            map.put("isChecked", false);
            data.add(map);
        }

        xinShuiRecyclerAdapter = new XinShuiGridRecyclerAdapter(mActivity, data);
        xinShuiRecyclerAdapter.setMaxChecked(12);
        recyclerView.setAdapter(xinShuiRecyclerAdapter);
    }

    @Override
    public void onLazyLoadData() {

    }

}
