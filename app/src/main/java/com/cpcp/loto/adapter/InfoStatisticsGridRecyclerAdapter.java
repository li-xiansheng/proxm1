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
 * 功能描述：资讯统计-网格适配
 */

public class InfoStatisticsGridRecyclerAdapter extends BaseRecycleViewAdapter {


    public InfoStatisticsGridRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context,data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_info_statistics_grid, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            View rootView = viewHolder.itemView;
            AppCompatImageView image = viewHolder.image;
            AppCompatTextView name = viewHolder.tvName;

            Map<String, Object> map = (Map<String, Object>) mListData.get(position);
            String strName = (String) map.get("name");
            int imgRes = (int) map.get("img");
            image.setImageResource(imgRes);
            name.setText(strName);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
