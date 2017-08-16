package com.cpcp.loto.fragment.xinshui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.HistoryRecyclerAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.XinshuiBean;
import com.cpcp.loto.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class HistoryItemFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<XinshuiBean> data = new ArrayList<>();
    HistoryRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));

        for (int i=1;i<10;i++){
            XinshuiBean bean = new XinshuiBean();
            bean.date = "2017-4-"+i+ "第"+i+"期";
            bean.shengfu = i%2==0?"胜":"负";
            bean.haoma = i%3==0?"号码":"大小";
            bean.choose = "双";
            data.add(bean);
        }
        mAdapter = new HistoryRecyclerAdapter(mContext,data);

//        toolBoxGridRecyclerAdapter = new ToolBoxGridRecyclerAdapter(mActivity, data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                Intent intent = new Intent((BaseActivity) mActivity, (Class<?>) data.get(position).get("uri"));
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onLazyLoadData() {

    }

}
