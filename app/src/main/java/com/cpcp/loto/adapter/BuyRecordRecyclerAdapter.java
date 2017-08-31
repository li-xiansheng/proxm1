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
import com.cpcp.loto.bean.BuyRecordBean;
import com.cpcp.loto.uihelper.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：工具箱网格适配器
 */

public class BuyRecordRecyclerAdapter extends BaseRecycleViewAdapter {


    public BuyRecordRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_buy_record, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String date = ((BuyRecordBean) mListData.get(position)).time;
            String chakan = ((BuyRecordBean) mListData.get(position)).title;
            String jifen = ((BuyRecordBean) mListData.get(position)).points;
            String head = "http://" + ((BuyRecordBean) mListData.get(position)).avatar;
            viewHolder.date.setText(date);
            viewHolder.chakan.setText(chakan);
            viewHolder.jifen.setText(jifen + "积分");

            Glide.with(mContext)
                    .load(head)
                    .placeholder(R.drawable.icon_default_head)
                    .transform(new GlideCircleTransform(mContext))
                    .into(viewHolder.salaryHead);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.salary_head)
        AppCompatImageView salaryHead;
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.chakan)
        AppCompatTextView chakan;
        @BindView(R.id.jifen)
        AppCompatTextView jifen;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
