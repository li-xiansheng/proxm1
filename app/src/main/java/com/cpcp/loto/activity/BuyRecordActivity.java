package com.cpcp.loto.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.BuyRecordRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.BuyRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：购买记录
 */

public class BuyRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BuyRecordRecyclerAdapter adapter;
    List<BuyRecordBean> data = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_buy_record;
    }

    @Override
    protected void initView() {
        setTitle("购买记录");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        for (int i=1;i<10;i++){
            BuyRecordBean bean = new BuyRecordBean();
            bean.date = "第20170402期"+i;
            bean.chakan = "查看:特码";
            bean.jifen = "-"+i+"积分";
            data.add(bean);
        }
        adapter = new BuyRecordRecyclerAdapter(this,data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
