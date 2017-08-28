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
import com.cpcp.loto.util.DateTimeUtils;
import com.cpcp.loto.util.DateUtils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：搅珠日期，日历适配器
 */

public class JiaozhuDateRecyclerAdapter extends BaseRecycleViewAdapter {

    private String time;
    int month;
    int year;

    /**
     * @param option_value
     */
    public void setTime(String option_value) {

        try {
            this.time = DateTimeUtils.DateConvertTimeStam(option_value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setMonth(int month) {
        this.month = month;
        year = DateUtils.getYear();
        int currentMonth = DateUtils.getMonth();
        if (currentMonth > month) {//如果当前月份，比展示的月份大，则跨年了
            year = year + 1;
        }

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
                    String currentDay = null;
                    try {
                        currentDay = DateTimeUtils.DateConvertTimeStam(year + "-" + month + "-" + values);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (currentDay != null && currentDay.equals(time)) {
                        ((ViewHolder) holder).tvDate.setBackgroundResource(R.drawable.shape_white_circle_fill);
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
