package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.entity.TrendAnalysisEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：五行走势数据适配
 */

public class WuXingTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public WuXingTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_wuxing_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_wuxing_trend, parent, false);
            viewHolder = new ViewHolderContent(view);
        }


        return viewHolder;
    }

    @Override
    protected void bindView(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderContent) {
            ViewHolderContent viewHolder = (ViewHolderContent) holder;
            if (mListData.get(position - 1) instanceof TrendAnalysisEntity) {
                TrendAnalysisEntity entity = (TrendAnalysisEntity) mListData.get(position - 1);
                String no = entity.getNo();
                String tema = entity.getTema();
                String yanse = entity.getYanse();
                String wuxing = entity.getWuxing();
                viewHolder.tvNo.setText(no + "");

                int color = 0;
                int grayColor = mContext.getResources().getColor(R.color.grayText);
                if ("lan".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.colorPrimaryDarkOne);

                } else if ("lv".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.green);

                } else if ("hong".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.red);

                } else {
                    color = mContext.getResources().getColor(R.color.grayText);
                }
                viewHolder.tvTema.setText(tema + "");
                viewHolder.tvTema.setTextColor(color);
                viewHolder.tvWuxing.setText(wuxing + "");
                viewHolder.tvWuxing.setTextColor(color);

                //
                setUI(viewHolder, color, grayColor);
                if ("金".equals(wuxing)) {
                    viewHolder.tvJin.setText(wuxing);
                    viewHolder.tvJin.setTextColor(color);
                } else if ("木".equals(wuxing)) {
                    viewHolder.tvMu.setText(wuxing);
                    viewHolder.tvMu.setTextColor(color);
                } else if ("水".equals(wuxing)) {
                    viewHolder.tvShui.setText(wuxing);
                    viewHolder.tvShui.setTextColor(color);
                } else if ("火".equals(wuxing)) {
                    viewHolder.tvHuo.setText(wuxing);
                    viewHolder.tvHuo.setTextColor(color);
                } else if ("土".equals(wuxing)) {
                    viewHolder.tvTu.setText(wuxing);
                    viewHolder.tvTu.setTextColor(color);
                }


            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int jinIndex = 0;
            int muIndex = 0;
            int shuiIndex = 0;
            int huoIndex = 0;
            int tuIndex = 0;


            for (TrendAnalysisEntity entity : listData) {
                String wuxing = entity.getWuxing();
                if ("金".equals(wuxing)) {
                    jinIndex += 1;
                } else if ("木".equals(wuxing)) {
                    muIndex += 1;
                } else if ("水".equals(wuxing)) {
                    shuiIndex += 1;
                } else if ("火".equals(wuxing)) {
                    huoIndex += 1;
                } else if ("土".equals(wuxing)) {
                    tuIndex += 1;
                }
                viewHolder.tvJin.setText(jinIndex + "");
                viewHolder.tvMu.setText(muIndex + "");
                viewHolder.tvShui.setText(shuiIndex + "");
                viewHolder.tvHuo.setText(huoIndex + "");
                viewHolder.tvTu.setText(tuIndex + "");


            }
        }

    }

    @Override
    public int getItemCount() {
        return (mListData == null) || mListData.size() == 0 ? 1 : mListData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
//        return super.getItemViewType(position);
    }

    private void setUI(ViewHolderContent viewHolder, int color, int grayColor) {
        viewHolder.tvJin.setText("--");
        viewHolder.tvJin.setTextColor(grayColor);
        viewHolder.tvMu.setText("--");
        viewHolder.tvMu.setTextColor(grayColor);
        viewHolder.tvShui.setText("--");
        viewHolder.tvShui.setTextColor(grayColor);
        viewHolder.tvHuo.setText("--");
        viewHolder.tvHuo.setTextColor(grayColor);
        viewHolder.tvTu.setText("--");
        viewHolder.tvTu.setTextColor(grayColor);


    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNo)
        AppCompatTextView tvNo;
        @BindView(R.id.tvTema)
        AppCompatTextView tvTema;
        @BindView(R.id.tvWuxing)
        AppCompatTextView tvWuxing;
        @BindView(R.id.tvJin)
        AppCompatTextView tvJin;
        @BindView(R.id.tvMu)
        AppCompatTextView tvMu;
        @BindView(R.id.tvShui)
        AppCompatTextView tvShui;
        @BindView(R.id.tvHuo)
        AppCompatTextView tvHuo;
        @BindView(R.id.tvTu)
        AppCompatTextView tvTu;


        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.tvJin)
        AppCompatTextView tvJin;
        @BindView(R.id.tvMu)
        AppCompatTextView tvMu;
        @BindView(R.id.tvShui)
        AppCompatTextView tvShui;
        @BindView(R.id.tvHuo)
        AppCompatTextView tvHuo;
        @BindView(R.id.tvTu)
        AppCompatTextView tvTu;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
