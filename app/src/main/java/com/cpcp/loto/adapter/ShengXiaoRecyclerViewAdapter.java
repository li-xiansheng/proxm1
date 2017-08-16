package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：生肖卡牌适配器
 */

public class ShengXiaoRecyclerViewAdapter extends BaseRecycleViewAdapter {


    public ShengXiaoRecyclerViewAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);

    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_shengxiao_grid, parent, false);
        ShengXiaoRecyclerViewAdapter.ViewHolder ViewHolder = new ShengXiaoRecyclerViewAdapter.ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShengXiaoRecyclerViewAdapter.ViewHolder) {
            ShengXiaoRecyclerViewAdapter.ViewHolder viewHolder = (ShengXiaoRecyclerViewAdapter.ViewHolder) holder;
            View rootView = viewHolder.itemView;
            AppCompatImageView image = viewHolder.imageView;

            Map<String, Object> map = (Map<String, Object>) mListData.get(position);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        AppCompatImageView imageView;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
