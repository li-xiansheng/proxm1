package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.DetailActivity;
import com.muzikun.lhfsyczl.activity.LoginActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.bean.CurrentRecommendBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：本期推荐
 */

public class CurrentRecyclerAdapter extends BaseRecycleViewAdapter {


    public CurrentRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    public void adData(List<?> data) {
        super.addData(data);
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_current, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final CurrentRecommendBean currentRecommendBean = ((CurrentRecommendBean) mListData.get(position));
            if (currentRecommendBean != null) {
                String leixing = currentRecommendBean.leixing;
                String liansheng = currentRecommendBean.liangsheng;
                final String fail = currentRecommendBean.fail;
                String total = currentRecommendBean.total;
                String shenglv = currentRecommendBean.shenglv;
                String title = currentRecommendBean.title;
                final String points = currentRecommendBean.points;
                String desc = currentRecommendBean.desc;
                final String readnum = currentRecommendBean.readnum;
                final String username = currentRecommendBean.username;




                int sheng = Integer.parseInt(total) * Integer.parseInt(shenglv) / 100;
                int fu = Integer.parseInt(total) - Integer.parseInt(total) * Integer.parseInt(shenglv) / 100;
                viewHolder.liangsheng.setText(leixing + "(最近已连胜" + liansheng + ")");
                viewHolder.shengfu.setText("总:" + total + " 胜:" + sheng + " 负" + fu);
                viewHolder.leixing.setText(desc);
                viewHolder.title.setText(title);
                if ("大小".equals(leixing)) {
                    viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                } else if ("单双".equals(leixing)) {
                    viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.red2));
                } else if ("号码".equals(leixing)) {
                    viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.gree2));
                } else {
                    viewHolder.leixing.setBackgroundColor(mContext.getResources().getColor(R.color.blue2));
                }
                viewHolder.points.setText("积分" + points + " 查看数:" + readnum);

                viewHolder.chakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String is_read = currentRecommendBean.is_read;
                        SPUtil spUtil = new SPUtil(mContext, Constants.USER_TABLE);

                        if (spUtil.getBoolean(UserDB.isLogin, false)) {
                            String tel = spUtil.getString(UserDB.TEL, "");
                            if ((!TextUtils.isEmpty(username) && username.equals(tel))||"1".equals(is_read)) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", currentRecommendBean.id);
                                bundle.putString("type", "current");
                                ((BaseActivity) mContext).jumpToActivity(DetailActivity.class, bundle, false);
                            } else {
                                new AlertDialog.Builder(mContext)
                                        .setTitle("提示")
                                        .setMessage("查看该推荐需要消耗" + points + "积分,是否查看？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                currentRecommendBean.is_read="1";//置为已读
                                                dialog.dismiss();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("id", currentRecommendBean.id);
                                                bundle.putString("type", "current");
                                                ((BaseActivity) mContext).jumpToActivity(DetailActivity.class, bundle, false);
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .create()
                                        .show();
                            }
                        } else {
                            //跳转到登录
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }
//


                    }
                });

            }

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liangsheng)
        AppCompatTextView liangsheng;
        @BindView(R.id.shengfu)
        AppCompatTextView shengfu;
        @BindView(R.id.leixing)
        AppCompatTextView leixing;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.points)
        AppCompatTextView points;
        @BindView(R.id.chakan)
        TextView chakan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
