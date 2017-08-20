package com.cpcp.loto.adapter;


import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.entity.ForumDetailEntity;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.util.KeyBoardUtils;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：论坛详细数据适配
 */

public class ForumDetailRecyclerViewAdapter extends BaseRecycleViewAdapter {

    private LinearLayout lilInput;
    private AppCompatEditText etInput;

    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    /**
     *
     * @param lilInput
     * @param etInput
     */
    public void setView(LinearLayout lilInput, AppCompatEditText etInput) {
        this.lilInput = lilInput;
        this.etInput = etInput;

    }

    public ForumDetailRecyclerViewAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_forum_head, parent, false);
            viewHolder = new ForumDetailRecyclerViewAdapter.ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_forum_reply, parent, false);
            viewHolder = new ForumDetailRecyclerViewAdapter.ViewHolderContent(view);
        }


        return viewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderContent) {
            ViewHolderContent viewHolder = (ViewHolderContent) holder;
            AppCompatImageView ivAvatar = viewHolder.ivAvatar;
            AppCompatTextView tvName = viewHolder.tvName;
            AppCompatTextView tvTime = viewHolder.tvTime;
            AppCompatTextView tvContent = viewHolder.tvContent;
            SelectedLayerTextView tvReplay = viewHolder.tvReply;
            RecyclerView recyclerView = viewHolder.recyclerView;
            //二级回复
            if (mListData.get(position) instanceof ForumDetailEntity.ReplyBean) {
                final ForumDetailEntity.ReplyBean replyBean = (ForumDetailEntity.ReplyBean) mListData.get(position);
                final ForumDetailEntity.UserinfoBean userinfo = replyBean.getUserinfo();
                String avatar = "";
                String userNicename = "";
                if (userinfo != null) {
                    avatar = userinfo.getAvatar();
                    userNicename = userinfo.getUser_nicename();
                }


                String createtime = replyBean.getCreatetime();
                String msg = replyBean.getMsg();
                if(avatar!=null&&!avatar.startsWith("http")){
                    avatar="http://"+avatar;
                }

                Glide.with(mContext)
                        .load(avatar)
                        .placeholder(R.drawable.icon_default_head)
                        .transform(new GlideCircleTransform(mContext))
                        .into(ivAvatar);
                if (TextUtils.isEmpty(userNicename)) {
                    tvName.setText("");
                } else {
                    tvName.setText(userNicename);
                }


                createtime = TextUtils.isEmpty(createtime) ? "" : createtime;

                tvTime.setText(createtime);
                msg = TextUtils.isEmpty(msg) ? "" : msg;

                tvContent.setText(msg);


                tvReplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lilInput.setVisibility(View.VISIBLE);
                        String reply = TextUtils.isEmpty(userinfo.getUser_nicename()) ? "" : userinfo.getUser_nicename();
                        etInput.setHint("回复：" + reply);
                        //当点击回复时，将所回复人信息绑定到输入框的tag对象中
                        JSONObject json = new JSONObject();
                        json.put("parent_id", replyBean.getId());
                        json.put("tworeply", "1");//二级回复时传1
                        etInput.setTag(json);
                        KeyBoardUtils.openKeyboard(etInput, mContext);


                    }
                });
                recyclerView.setVisibility(View.VISIBLE);
                initRecyclerView(recyclerView, replyBean.getTworeply());

            }
        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            AppCompatImageView ivAvatar = viewHolder.ivAvatar;
            AppCompatTextView tvName = viewHolder.tvName;
            AppCompatTextView tvTime = viewHolder.tvTime;
            AppCompatTextView tvContent = viewHolder.tvContent;
            AppCompatImageView ivReplay = viewHolder.ivReply;
            //一级回复
            if (mListData.get(position) instanceof ForumDetailEntity.ReplyBean) {
                final ForumDetailEntity.ReplyBean replyBean = (ForumDetailEntity.ReplyBean) mListData.get(position);
                final ForumDetailEntity.UserinfoBean userinfo = replyBean.getUserinfo();
                String avatar = "";
                String userNicename = "";
                if (userinfo != null) {
                    avatar = userinfo.getAvatar();
                    userNicename = userinfo.getUser_nicename();
                }


                String createtime = replyBean.getCreatetime();
                String msg = replyBean.getMsg();

                if(avatar!=null&&!avatar.startsWith("http")){
                    avatar="http://"+avatar;
                }
                Glide.with(mContext)
                        .load(avatar)
                        .placeholder(R.drawable.icon_default_head)
                        .transform(new GlideCircleTransform(mContext))
                        .into(ivAvatar);
                if (TextUtils.isEmpty(userNicename)) {
                    tvName.setText("");
                } else {
                    tvName.setText(userNicename);
                }


                createtime = TextUtils.isEmpty(createtime) ? "" : createtime;

                tvTime.setText(createtime);
                msg = TextUtils.isEmpty(msg) ? "" : msg;

                tvContent.setText(msg);

                if (mListData.size() > 1) {
                    viewHolder.lilNew.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lilNew.setVisibility(View.GONE);
                }

                ivReplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lilInput.setVisibility(View.VISIBLE);
                        String reply = TextUtils.isEmpty(userinfo.getUser_nicename()) ? "" : userinfo.getUser_nicename();
                        etInput.setHint("回复：" + reply);
                        //当点击回复时，将所回复人信息绑定到输入框的tag对象中
                        JSONObject json = new JSONObject();
                        json.put("parent_id", replyBean.getId());

                        etInput.setTag(json);
                        KeyBoardUtils.openKeyboard(etInput, mContext);
                    }
                });
            }

        }
    }

    /**
     * 子列
     *
     * @param recyclerView
     * @param tworeply
     */
    private void initRecyclerView(RecyclerView recyclerView, List<ForumDetailEntity.ReplyBean.TworeplyBean> tworeply) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mListData = new ArrayList<>();
        ForumDetailChildRecyclerAdapter communityDetailChildRecyclerAdapter = new ForumDetailChildRecyclerAdapter(mContext, tworeply);
        recyclerView.setAdapter(communityDetailChildRecyclerAdapter);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
    }


    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        AppCompatImageView ivAvatar;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;
        @BindView(R.id.tvTime)
        AppCompatTextView tvTime;
        @BindView(R.id.tvReply)
        SelectedLayerTextView tvReply;
        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        AppCompatImageView ivAvatar;
        @BindView(R.id.tvName)
        AppCompatTextView tvName;
        @BindView(R.id.tvTime)
        AppCompatTextView tvTime;
        @BindView(R.id.ivReply)
        AppCompatImageView ivReply;
        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;
        @BindView(R.id.lilNew)
        LinearLayout lilNew;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
