package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.util.BallColorUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：摇一摇数据适配器
 */

public class ShakeLotteryRecyclerAdapter extends BaseRecycleViewAdapter {


    public ShakeLotteryRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);

    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_shake_lottery, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AppCompatTextView name = viewHolder.tvNumber;
            if (mListData.get(position) instanceof Integer) {
                Integer number = (Integer) mListData.get(position);
                String num=String.format("%02d",number.intValue());
                name.setText(num);
                BallColorUtil.ballColor(num,name);


            }


        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNumber)
        AppCompatTextView tvNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
