package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.entity.ForumEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：论坛信息适配器
 */

public class ForumRecyclerAdapter extends BaseRecycleViewAdapter {


    public ForumRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_forum, parent, false);
        return new ViewHolderContent(view);
    }

    @Override
    protected void bindView(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderContent) {
            ViewHolderContent viewHolder = (ViewHolderContent) holder;
            if (mListData.get(position) instanceof ForumEntity) {
                ForumEntity entity = (ForumEntity) mListData.get(position);

                String zhiding = entity.getIs_zhiding();
                String jinghua = entity.getIs_jinghua();
                String qishu = entity.getQishu();

                String title = entity.getTitle();
                String fullName = entity.getFull_name();
                String time = entity.getCreatetime();
                String countreply = entity.getCountreply();

                qishu = TextUtils.isEmpty(qishu) ? "" : qishu;
                title = TextUtils.isEmpty(title) ? "" : title;
                fullName = TextUtils.isEmpty(fullName) ? "" : fullName;
                time = TextUtils.isEmpty(time) ? "" : time;
                countreply = TextUtils.isEmpty(countreply) ? "0" : countreply;

                if ("1".equals(zhiding)) {
                    viewHolder.tvType.setText("置顶");
                    viewHolder.tvType.setBackgroundResource(R.drawable.shape_arc2_red);
                } else if ("1".equals(jinghua)) {
                    viewHolder.tvType.setText("精华");
                    viewHolder.tvType.setBackgroundResource(R.drawable.shape_arc2_blue);
                } else {
                    viewHolder.tvType.setText(qishu);
                    viewHolder.tvType.setBackgroundResource(R.drawable.shape_arc2_green);
                }

                viewHolder.tvTitle.setText(title);
                viewHolder.tvName.setText(fullName);
                viewHolder.tvTime.setText(time);
                viewHolder.tvReply.setText("回复:" + countreply);


            }


        }
    }


    /**
     * 内容实体
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {

        @BindView(R.id.tvType)
        AppCompatTextView tvType;
        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;
        @BindView(R.id.tvTime)
        AppCompatTextView tvTime;
        @BindView(R.id.tvReply)
        AppCompatTextView tvReply;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
