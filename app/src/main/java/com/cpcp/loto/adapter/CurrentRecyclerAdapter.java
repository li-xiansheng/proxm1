package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：工具箱网格适配器
 */

public class CurrentRecyclerAdapter extends BaseRecycleViewAdapter {


    public CurrentRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_current, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AppCompatTextView name = viewHolder.date;
            String strName = (String) mListData.get(position);
            name.setText(strName);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        AppCompatTextView date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
