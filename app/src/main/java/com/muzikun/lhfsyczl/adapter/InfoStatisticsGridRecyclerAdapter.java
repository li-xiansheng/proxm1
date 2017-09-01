package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：资讯统计-网格适配
 */

public class InfoStatisticsGridRecyclerAdapter extends BaseRecycleViewAdapter {

    private int currentSelectedPosition;

    public InfoStatisticsGridRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_info_statistics_grid, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            View rootView = viewHolder.itemView;
            AppCompatImageView image = viewHolder.image;
            AppCompatTextView name = viewHolder.tvName;
            Map<String, Object> map = (Map<String, Object>) mListData.get(position);
            String strName = (String) map.get("name");
            int imgRes = (int) map.get("img");
            name.setText(strName);
            if (position!=currentSelectedPosition){

                image.setImageResource(imgRes);
                name.setTextColor(mContext.getResources().getColor(R.color.grayText));
            }else{
                switch (currentSelectedPosition) {
                    case 0:
                        image.setImageResource(R.drawable.item_tong_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 1:
                        image.setImageResource(R.drawable.item_shuxing_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 2:
                        image.setImageResource(R.drawable.item_te_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 3:
                        image.setImageResource(R.drawable.item_zheng_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 4:
                        image.setImageResource(R.drawable.item_da_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 5:
                        image.setImageResource(R.drawable.item_sheng_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 6:
                        image.setImageResource(R.drawable.item_xiao_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 7:
                        image.setImageResource(R.drawable.item_bo_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 8:
                        image.setImageResource(R.drawable.item_se_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 9:
                        image.setImageResource(R.drawable.item_liang_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 10:
                        image.setImageResource(R.drawable.item_wei_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 11:
                        image.setImageResource(R.drawable.item_shu_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 12:
                        image.setImageResource(R.drawable.item_fen_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 13:
                        image.setImageResource(R.drawable.item_duan_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 14:
                        image.setImageResource(R.drawable.item_qin_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 15:
                        image.setImageResource(R.drawable.item_lianma_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;
                    case 16:
                        image.setImageResource(R.drawable.item_lianxiao_checked);
                        name.setTextColor(mContext.getResources().getColor(R.color.redTextTab));
                        break;

                }
            }





        }
    }

    /**
     * 设置当前被选中的position
     *
     * @param currentSelectedPosition
     */
    public void setSelectedPosition(int currentSelectedPosition) {
        this.currentSelectedPosition = currentSelectedPosition;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
