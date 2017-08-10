package com.cpcp.loto.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.listener.OnItemLongClickListener;

import java.util.List;


/**
 * 功能描述：列表式数据RecycleView的基础适配器
 */

abstract public class BaseRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //
    public Context mContext;
    public List<?> mListData;
    public LayoutInflater mLayoutInflater;

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void BaseRecycleViewAdapter(Context context, List<?> data) {
        this.mContext = context;
        this.mListData = data;
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return baseCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        View rootView = holder.itemView;
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }

            }
        });
        rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v, holder.getLayoutPosition());
                }
                return true;
            }
        });
        bindView(holder, position);
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    protected abstract RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindView(RecyclerView.ViewHolder holder, int position);

}
