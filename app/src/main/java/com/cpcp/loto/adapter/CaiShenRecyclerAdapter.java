package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.bean.BuyRecordBean;
import com.cpcp.loto.entity.CaiShenEntity;
import com.cpcp.loto.uihelper.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：财神数据适配器
 */

public class CaiShenRecyclerAdapter extends BaseRecycleViewAdapter {


    public CaiShenRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_cai_shen, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            CaiShenEntity entity = ((CaiShenEntity) mListData.get(position));
            String avatar = entity.getAvatar();
            String name = entity.getUser_nicename();
            name = TextUtils.isEmpty(name) ? "" : name;
            if (avatar != null) {
                Glide.with(mContext)
                        .load(avatar)
                        .placeholder(R.drawable.icon_default_head)
                        .transform(new GlideCircleTransform(mContext))
                        .into(viewHolder.ivHead);
            }
            viewHolder.tvName.setText(name);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivHead)
        AppCompatImageView ivHead;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
