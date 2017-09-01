package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.bean.ChangeRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：工具箱网格适配器
 */

public class ChangeRecordRecyclerAdapter extends BaseRecycleViewAdapter {




    public ChangeRecordRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_change_record, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String date = ((ChangeRecordBean) mListData.get(position)).createtime;
            String number = ((ChangeRecordBean) mListData.get(position)).order_id;
            String jifen = ((ChangeRecordBean) mListData.get(position)).points;
            String platform = ((ChangeRecordBean) mListData.get(position)).pingtainame;
            String jine = (Integer.parseInt(jifen)/10)+"";
            viewHolder.date.setText(" "+date);
            viewHolder.number.setText(" "+number);
            viewHolder.jifen.setText(" -"+jifen+"积分");
            viewHolder.platform.setText(" "+platform);
            viewHolder.jine.setText(" "+jine+"元");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        AppCompatTextView number;
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.jifen)
        AppCompatTextView jifen;
        @BindView(R.id.platform)
        AppCompatTextView platform;
        @BindView(R.id.jine)
        AppCompatTextView jine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
