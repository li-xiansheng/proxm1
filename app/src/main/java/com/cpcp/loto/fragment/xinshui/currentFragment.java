package com.cpcp.loto.fragment.xinshui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.CurrentRecyclerAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class currentFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<String> data = new ArrayList<>();
    CurrentRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        for (int i=1;i<10;i++){
            data.add("2017-4-"+i+ "第"+1+"期");
        }
        mAdapter = new CurrentRecyclerAdapter(mContext,data);

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
