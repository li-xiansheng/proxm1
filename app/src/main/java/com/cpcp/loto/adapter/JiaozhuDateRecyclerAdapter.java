package com.cpcp.loto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：搅珠日期，日历适配器
 */

public class JiaozhuDateRecyclerAdapter extends BaseRecycleViewAdapter {

    private String time;
    private int month;

    /**
     * @param month
     * @param option_value
     */
    public void setTime(int month, String option_value) {
        this.time = option_value;
        this.month = month;
    }

    public JiaozhuDateRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);


    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_jiao_zhu_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            int values = (Integer) mListData.get(position);
            if (values > 0) {
                ((ViewHolder) holder).tvDate.setText(values + "");
                if (!TextUtils.isEmpty(time)) {
                    String[] dates = time.split("-");

                    if (dates != null && dates.length > 2 &&
                            month != 0 && dates[1].equals(month + "") &&
                            dates[2].equals(values + "")) {
                        ((ViewHolder) holder).tvDate.setBackgroundResource(R.color.white);
                        ((ViewHolder) holder).tvDate.setTextColor(Color.RED);

                    } else {
                        ((ViewHolder) holder).tvDate.setBackgroundResource(R.color.transparent);
                        ((ViewHolder) holder).tvDate.setTextColor(Color.WHITE);

                    }
                } else {
                    ((ViewHolder) holder).tvDate.setBackgroundResource(R.color.transparent);
                    ((ViewHolder) holder).tvDate.setTextColor(Color.WHITE);

                }
            } else {
                ((ViewHolder) holder).tvDate.setText("");
            }

        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        AppCompatTextView tvDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
