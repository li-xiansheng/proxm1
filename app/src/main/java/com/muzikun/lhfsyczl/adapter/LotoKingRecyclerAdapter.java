package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.entity.LotoKingEntity;
import com.muzikun.lhfsyczl.uihelper.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            AppCompatTextView tvRanking = viewHolder.tvRanking;
            AppCompatImageView ivAvatar = viewHolder.ivAvatar;
            AppCompatTextView tvName = viewHolder.tvName;
            AppCompatTextView tvData = viewHolder.tvData;
            AppCompatTextView tvWinRate = viewHolder.tvWinRate;


            if (mListData.get(position) instanceof LotoKingEntity) {

                LotoKingEntity entity = (LotoKingEntity) mListData.get(position);
                String liansheng = entity.getLiansheng();

                String total = entity.getTotal();
                String fail = entity.getFail();
                String success = entity.getSuccess();
                String shenglv = entity.getShenglv();
                LotoKingEntity.UserinfoBean userinfoBean = entity.getUserinfo();
                String avatar = "";
                String name = "";
                if (userinfoBean != null) {
                    avatar = userinfoBean.getAvatar();
                    name = userinfoBean.getUser_nicename();
                    if (!TextUtils.isEmpty(avatar) && !avatar.startsWith("http")) {
                        avatar = "http://" + avatar;
                    }
                    name = TextUtils.isEmpty(name) ? "" : name;
                }

                if (position == 0) {
                    tvRanking.setBackgroundResource(R.drawable.icon_ranking_no1);
                    tvRanking.setText("");
                } else if (position == 1) {
                    tvRanking.setBackgroundResource(R.drawable.icon_ranking_no2);
                    tvRanking.setText("");
                } else if (position == 2) {
                    tvRanking.setBackgroundResource(R.drawable.icon_ranking_no3);
                    tvRanking.setText("");
                } else {
                    tvRanking.setBackgroundResource(R.color.transparent);
                    tvRanking.setText((position + 1) + "");
                }

                if (!TextUtils.isEmpty(avatar)) {
                    Glide.with(mContext)
                            .load(avatar)
                            .placeholder(R.drawable.icon_default_head)
                            .transform(new GlideCircleTransform(mContext))
                            .into(ivAvatar);
                }
                tvName.setText(name);
                tvData.setText("总:" + total + " 胜负:" + success + "/" + fail + " 最大连胜" + liansheng);
                tvWinRate.setText(shenglv + "%");


            }

        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRanking)
        AppCompatTextView tvRanking;
        @BindView(R.id.ivAvatar)
        AppCompatImageView ivAvatar;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;
        @BindView(R.id.tvData)
        AppCompatTextView tvData;
        @BindView(R.id.tvWinRate)
        AppCompatTextView tvWinRate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
