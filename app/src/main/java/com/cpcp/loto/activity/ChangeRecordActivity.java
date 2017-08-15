package com.cpcp.loto.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ChangeRecordRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.ChangeRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：转换记录
 */

public class ChangeRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ChangeRecordRecyclerAdapter adapter;
    List<ChangeRecordBean> data = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_record;
    }

    @Override
    protected void initView() {
        setTitle("转换记录");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        for (int i=1;i<10;i++){
            ChangeRecordBean bean = new ChangeRecordBean();
            bean.number = "bet365ijnhgyrrtd"+i;
            bean.date = "20170402="+i;
            bean.jifen = "-"+i+"积分";
            bean.platform = "bet365体育彩票";
            bean.jine = i+"元";
            data.add(bean);
        }
        adapter = new ChangeRecordRecyclerAdapter(this,data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

}
