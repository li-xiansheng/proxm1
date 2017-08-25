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
 * 功能描述：波肖转盘网格适配器
 */

public class TurntableRecyclerAdapter extends BaseRecycleViewAdapter {

    public TurntableRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_turntable, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            if(mListData.get(position) instanceof Map){
                Map<String,Object> map= (Map<String, Object>) mListData.get(position);
                if (map.get("img")!=null){
                    int imgRes= (int) map.get("img");
                    boolean isChecked= (boolean) map.get("isChecked");
                        ((ViewHolder) holder).imageView.setBackgroundResource(imgRes);
                    if (isChecked){
                        ((ViewHolder) holder).imageView.setImageResource(R.drawable.icon_luck_kuang);
                    }else{
                        ((ViewHolder) holder).imageView.setImageResource(0);
                    }
                }else{
                    ((ViewHolder) holder).imageView.setBackgroundResource(0);
                    ((ViewHolder) holder).imageView.setImageResource(0);

                }


            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        AppCompatImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
