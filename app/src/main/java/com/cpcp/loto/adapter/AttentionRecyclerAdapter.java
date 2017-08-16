package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.bean.AttentionBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：工具箱网格适配器
 */

public class AttentionRecyclerAdapter extends BaseRecycleViewAdapter {




    public AttentionRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_attention, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String strName = ((AttentionBean) mListData.get(position)).name;
            String total = ((AttentionBean) mListData.get(position)).total;
            String shengfu = ((AttentionBean) mListData.get(position)).shengfu;
            String head = ((AttentionBean) mListData.get(position)).headUrl;
            viewHolder.name.setText(strName);
            viewHolder.total.setText("总共:"+total);
            viewHolder.shengfu.setText("胜负:"+shengfu);
            if (head!=null){
                Glide.with(mContext).load(head).into(viewHolder.salaryHead);
            }else {
                viewHolder.salaryHead.setImageResource(R.drawable.icon_default_head);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.salary_head)
        AppCompatImageView salaryHead;
        @BindView(R.id.name)
        AppCompatTextView name;
        @BindView(R.id.total)
        AppCompatTextView total;
        @BindView(R.id.shengfu)
        AppCompatTextView shengfu;
        @BindView(R.id.quxiao)
        AppCompatTextView quxiao;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
