package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.entity.ForumDetailEntity;
import com.cpcp.loto.entity.ForumEntity;
import com.cpcp.loto.util.DateTimeUtils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：论坛数据详情字数据适配
 */

public class ForumDetailChildRecyclerAdapter extends BaseRecycleViewAdapter {

    public ForumDetailChildRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_forum_detail_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

             AppCompatTextView tvMsg = viewHolder.tvMsg;


            if (mListData.get(position) instanceof ForumDetailEntity.ReplyBean.TworeplyBean) {

                ForumDetailEntity.ReplyBean.TworeplyBean replyBean =
                        (ForumDetailEntity.ReplyBean.TworeplyBean) mListData.get(position);

                String msg = replyBean.getMsg();
                String full_name = replyBean.getFull_name();
                //
                full_name = TextUtils.isEmpty(full_name) ? "xxx" : full_name;

                //
                msg = TextUtils.isEmpty(msg) ? "" : msg;
//                tvMsg.setText(msg);
                SpannableStringBuilder builder = new SpannableStringBuilder(full_name+":");
                builder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary)), 0, full_name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(msg);
                tvMsg.setText(builder);
            }
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMsg)
        AppCompatTextView tvMsg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
