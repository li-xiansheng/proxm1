package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.bean.AttentionBean;
import com.muzikun.lhfsyczl.uihelper.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：粉丝数据适配器，我的关注
 */

public class FansRecyclerAdapter extends BaseRecycleViewAdapter {


    public FansRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_attention, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String strName = ((AttentionBean) mListData.get(position)).user_nicename;
            String total = ((AttentionBean) mListData.get(position)).total;
            String success = ((AttentionBean) mListData.get(position)).success;
            String fail = ((AttentionBean) mListData.get(position)).fail;
            String avatar = ((AttentionBean) mListData.get(position)).avatar;
            if (avatar != null && !avatar.startsWith("http")) {
                avatar = "http://" + avatar;
            }
            viewHolder.name.setText(strName);
            viewHolder.total.setText("总:" + total);
            viewHolder.shengfu.setText("胜负:" + success + "/" + fail);

            Glide.with(mContext)
                    .load(avatar)
                    .placeholder(R.drawable.icon_default_head)
                    .transform(new GlideCircleTransform(mContext))
                    .into(viewHolder.salaryHead);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.salary_head)
        AppCompatImageView salaryHead;
        @BindView(R.id.name)
        AppCompatTextView name;
        @BindView(R.id.total)
        AppCompatTextView total;
        @BindView(R.id.shengfu)
        AppCompatTextView shengfu;
        @BindView(R.id.quxiao)
        AppCompatTextView quxiao;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            quxiao.setVisibility(View.GONE);
        }
    }
}
