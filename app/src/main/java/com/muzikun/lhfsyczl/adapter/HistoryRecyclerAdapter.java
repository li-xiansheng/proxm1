package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.DetailActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.bean.XinshuiBean;

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
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String date = ((XinshuiBean) mListData.get(position)).qishu+((XinshuiBean) mListData.get(position)).periods;
            String shengfu ="";
            if (((XinshuiBean) mListData.get(position)).shengfu == 1){
                shengfu = "胜";
            }else {
                shengfu = "负";
            }
            String type = ((XinshuiBean) mListData.get(position)).type;
            String typeStr = "";
            if ("1".equals(type)){
                typeStr = "大小";
            }else if ("2".equals(type)){
                typeStr = "单双";
            }else if ("3".equals(type)){
                typeStr = "生肖";
            }else {
                typeStr = "号码";
            }
            String choose =  ((XinshuiBean) mListData.get(position)).forecast;
            viewHolder.date.setText(date);
            viewHolder.shengfu.setText(shengfu);
            viewHolder.leixing.setText(typeStr);
            viewHolder.choose.setText(choose);

            viewHolder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id",((XinshuiBean) mListData.get(position)).id);
                    bundle.putString("type","history");
                    ((BaseActivity) mContext).jumpToActivity(DetailActivity.class,bundle,false);
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.shengfu)
        TextView shengfu;
        @BindView(R.id.leixing)
        TextView leixing;
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
