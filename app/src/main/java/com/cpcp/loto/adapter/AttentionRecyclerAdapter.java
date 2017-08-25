package com.cpcp.loto.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.activity.AttentionActivity;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.bean.AttentionBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：我的关注
 */

public class AttentionRecyclerAdapter extends BaseRecycleViewAdapter {


    AttentionActivity activity;


    public AttentionRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
        activity = (AttentionActivity)context;
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
            String avatar = "http://"+((AttentionBean) mListData.get(position)).avatar;
            viewHolder.name.setText(strName);
            viewHolder.total.setText("总:"+total);
            viewHolder.shengfu.setText("胜负:"+success+"/"+fail);
            if (avatar!=null){
                Glide.with(mContext)
                        .load(avatar)
                        .transform(new GlideCircleTransform(mContext))
                        .into(viewHolder.salaryHead);
            }else {
                viewHolder.salaryHead.setImageResource(R.drawable.icon_default_head);
            }

            viewHolder.quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelAttention((AttentionBean) mListData.get(position));
                }
            });
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
        }
    }

    private void cancelAttention(AttentionBean bean){
        LoadingDialog.showDialog(activity);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("friendname", bean.mobile);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.cancelAttention(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return activity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(activity);
                        if (response.getFlag() == 1) {
                            ToastUtils.show("已取消关注");
                            activity.getAttentionData();
                        }
                    }

                });
    }
}
