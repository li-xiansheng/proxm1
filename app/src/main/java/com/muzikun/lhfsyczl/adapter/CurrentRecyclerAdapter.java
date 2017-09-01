package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.DetailActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.bean.CurrentRecommendBean;

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

    public void adData(List<?> data){
        super.addData(data);
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_current, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String leixing = ((CurrentRecommendBean) mListData.get(position)).leixing;
            String liansheng = ((CurrentRecommendBean) mListData.get(position)).liangsheng;
            String fail = ((CurrentRecommendBean) mListData.get(position)).fail;
            String total = ((CurrentRecommendBean) mListData.get(position)).total;
            String shenglv = ((CurrentRecommendBean) mListData.get(position)).shenglv;
            String title = ((CurrentRecommendBean) mListData.get(position)).title;
            String points = ((CurrentRecommendBean) mListData.get(position)).points;
            String desc = ((CurrentRecommendBean) mListData.get(position)).desc;
            String readnum = ((CurrentRecommendBean) mListData.get(position)).readnum;

            int sheng = Integer.parseInt(total)*Integer.parseInt(shenglv)/100;
            int fu  = Integer.parseInt(total) - Integer.parseInt(total)*Integer.parseInt(shenglv)/100;
            viewHolder.liangsheng.setText(leixing+"(最近已连胜"+liansheng+")");
            viewHolder.shengfu.setText("总:"+total+" 胜:"+sheng+" 负"+fu);
            viewHolder.leixing.setText(desc);
            viewHolder.title.setText(title);
            if ("大小".equals(leixing)){
                viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            }else if ("单双".equals(leixing)){
                viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.red2));
            }else if ("号码".equals(leixing)){
                viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.gree2));
            }else {
                viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.blue2));
            }
            viewHolder.points.setText("积分"+points+" 查看数:"+readnum);

            viewHolder.chakan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("CurrentRecyclerAdapter","点击——---》"+position);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",((CurrentRecommendBean) mListData.get(position)).id);
                    bundle.putString("type","current");
                    ((BaseActivity) mContext).jumpToActivity(DetailActivity.class,bundle,false);
                }
            });

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liangsheng)
        AppCompatTextView liangsheng;
        @BindView(R.id.shengfu)
        AppCompatTextView shengfu;
        @BindView(R.id.leixing)
        AppCompatTextView leixing;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.points)
        AppCompatTextView points;
        @BindView(R.id.chakan)
        TextView chakan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
