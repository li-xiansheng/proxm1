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
import com.cpcp.loto.config.HostConfig;
import com.cpcp.loto.entity.WinningEntity;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：连胜榜数据适配器
 */

public class WinningRecyclerAdapter extends BaseRecycleViewAdapter {
    /**
     * 设定大小为1，单双为2，生肖为3，号码为4
     */
    private String type = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WinningRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_winning, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AppCompatImageView ivAvatar = viewHolder.ivAvatar;
            AppCompatTextView tvNickName = viewHolder.tvNickName;
            AppCompatTextView tvTitle1 = viewHolder.tvTitle;
            AppCompatTextView tvWinning = viewHolder.tvWinning;
            AppCompatTextView tvType = viewHolder.tvType;


            if (mListData.get(position) instanceof WinningEntity) {
                WinningEntity entity = (WinningEntity) mListData.get(position);
                WinningEntity.UserinfoBean userinfoBean = entity.getUserinfo();
                String avatar = userinfoBean.getAvatar();
                String nickName = userinfoBean.getUser_nicename();
                String title = entity.getDesc();
                String liansheng = entity.getLiansheng();
                if (avatar != null && !avatar.contains("http")) {
                    avatar = "http://" + avatar;
                }

                tvNickName.setText(nickName);
                tvTitle1.setText(title);
                tvWinning.setText("连胜 " + liansheng);

                Glide.with(mContext)
                        .load(avatar)
                        .placeholder(R.drawable.icon_default_head)
                        .transform(new GlideCircleTransform(mContext))
                        .into(ivAvatar);

                if ("1".equals(type)) {
                    tvType.setBackgroundResource(R.drawable.shape_arc2_yellow);
                    tvType.setText("大小");
                } else if ("2".equals(type)) {
                    tvType.setBackgroundResource(R.drawable.shape_arc2_red);
                    tvType.setText("单双");

                } else if ("3".equals(type)) {
                    tvType.setBackgroundResource(R.drawable.shape_arc2_blue);
                    tvType.setText("生肖");

                } else if ("4".equals(type)) {
                    tvType.setBackgroundResource(R.drawable.shape_arc2_green);
                    tvType.setText("号码");

                }


            }

        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivAvatar)
        AppCompatImageView ivAvatar;
        @BindView(R.id.tvNickName)
        AppCompatTextView tvNickName;
        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;
        @BindView(R.id.tvWinning)
        AppCompatTextView tvWinning;
        @BindView(R.id.tvType)
        AppCompatTextView tvType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
