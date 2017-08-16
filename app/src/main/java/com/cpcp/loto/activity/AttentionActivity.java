package com.cpcp.loto.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.AttentionRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.AttentionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：我的关注
 */

public class AttentionActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    AttentionRecyclerAdapter adapter;
    List<AttentionBean> data = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        setTitle("我的关注");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        for (int i=1;i<10;i++){
            AttentionBean bean = new AttentionBean();
            bean.name = "不是就不是"+i;
            bean.total = i*16+"";
            bean.shengfu = "34/"+25*i;
            data.add(bean);
        }
        adapter = new AttentionRecyclerAdapter(this,data);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }


}
