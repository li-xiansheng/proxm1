package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.entity.LotoKingEntity;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：六合王数据适配
 */

public class LotoKingRecyclerAdapter extends BaseRecycleViewAdapter {


    public LotoKingRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_loto_king, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AppCompatTextView tvTypeLatelyWinning = viewHolder.tvTypeLatelyWinning;
            AppCompatTextView tvData = viewHolder.tvData;
            AppCompatTextView tvType = viewHolder.tvType;
            AppCompatTextView tvTitle = viewHolder.tvTitle;
            AppCompatTextView tvReadAndScore = viewHolder.tvReadAndScore;
            SelectedLayerTextView tvLook = viewHolder.tvLook;
            tvLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                    }
                }
            });
            View rootView = holder.itemView;
            rootView.setOnClickListener(null);

            if (mListData.get(position) instanceof LotoKingEntity) {
                LotoKingEntity entity = (LotoKingEntity) mListData.get(position);
                String liansheng = entity.getLiansheng();

                String total = entity.getTotal();
                String fail = entity.getFail();
                String success = entity.getSuccess();
                String shenglv = entity.getShenglv();

                tvTypeLatelyWinning.setText("类型（最近已连胜）" + liansheng);
                tvData.setText("总:" + total + " 胜:" + success + " 负:" + fail + " 胜率:" + shenglv);


            }

        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTypeLatelyWinning)
        AppCompatTextView tvTypeLatelyWinning;
        @BindView(R.id.tvData)
        AppCompatTextView tvData;
        @BindView(R.id.tvType)
        AppCompatTextView tvType;
        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;
        @BindView(R.id.tvReadAndScore)
        AppCompatTextView tvReadAndScore;
        @BindView(R.id.tvLook)
        SelectedLayerTextView tvLook;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
