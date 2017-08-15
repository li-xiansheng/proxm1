package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.bean.XinshuiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：工具箱网格适配器
 */

public class HistoryRecyclerAdapter extends BaseRecycleViewAdapter {




    public HistoryRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_history, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AppCompatTextView name = viewHolder.date;
            String strName = ((XinshuiBean) mListData.get(position)).date;
            String shengfu = ((XinshuiBean) mListData.get(position)).shengfu;
            String haoma = ((XinshuiBean) mListData.get(position)).haoma;
            String choose = ((XinshuiBean) mListData.get(position)).choose;
            name.setText(strName);
            viewHolder.shengfu.setText(shengfu);
            viewHolder.haoma.setText(haoma);
            viewHolder.choose.setText(choose);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.shengfu)
        TextView shengfu;
        @BindView(R.id.haoma)
        TextView haoma;
        @BindView(R.id.choose)
        TextView choose;
        @BindView(R.id.check)
        TextView check;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
