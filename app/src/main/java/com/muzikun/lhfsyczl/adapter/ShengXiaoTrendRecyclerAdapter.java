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
 * 功能描述：生肖走势数据适配
 */

public class ShengXiaoTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public ShengXiaoTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_shengxiao_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_shengxiao_trend, parent, false);
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
                String name = entity.getShengxiao();
                viewHolder.tvNo.setText(no);
                viewHolder.tvTema.setText(tema);
                int color = 0;
                int grayColor = mContext.getResources().getColor(R.color.grayText);
                if ("lan".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.colorPrimaryDark);
                } else if ("lv".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.green);

                } else if ("hong".equals(yanse)) {
                    color = mContext.getResources().getColor(R.color.red);
                } else {
                    color = mContext.getResources().getColor(R.color.grayText);
                }
                viewHolder.tvTema.setTextColor(color);
                //
                setUI(viewHolder, color, grayColor);
                if ("鼠".equals(name)) {
                    viewHolder.tvShu.setText("鼠");
                    viewHolder.tvShu.setTextColor(color);

                } else if ("牛".equals(name)) {

                    viewHolder.tvNiu.setText("牛");
                    viewHolder.tvNiu.setTextColor(color);

                } else if ("虎".equals(name)) {

                    viewHolder.tvHu.setText("虎");
                    viewHolder.tvHu.setTextColor(color);

                } else if ("兔".equals(name)) {

                    viewHolder.tvTu.setText("兔");
                    viewHolder.tvTu.setTextColor(color);

                } else if ("龙".equals(name)) {

                    viewHolder.tvLong.setText("龙");
                    viewHolder.tvLong.setTextColor(color);

                } else if ("蛇".equals(name)) {

                    viewHolder.tvShe.setText("蛇");
                    viewHolder.tvShe.setTextColor(color);

                } else if ("马".equals(name)) {

                    viewHolder.tvMa.setText("马");
                    viewHolder.tvMa.setTextColor(color);

                } else if ("羊".equals(name)) {

                    viewHolder.tvYang.setText("羊");
                    viewHolder.tvYang.setTextColor(color);

                } else if ("猴".equals(name)) {

                    viewHolder.tvHou.setText("猴");
                    viewHolder.tvHou.setTextColor(color);

                } else if ("鸡".equals(name)) {
                    viewHolder.tvJi.setText("鸡");
                    viewHolder.tvJi.setTextColor(color);

                } else if ("狗".equals(name)) {
                    viewHolder.tvGou.setText("狗");
                    viewHolder.tvGou.setTextColor(color);

                } else if ("猪".equals(name)) {
                    viewHolder.tvZhu.setText("猪");
                    viewHolder.tvZhu.setTextColor(color);
                }

            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int shuIndex = 0;
            int niuIndex = 0;
            int huIndex = 0;
            int tuIndex = 0;
            int longIndex = 0;
            int sheIndex = 0;
            int maIndex = 0;
            int yangIndex = 0;
            int houIndex = 0;
            int jiIndex = 0;
            int gouIndex = 0;
            int zhuIndex = 0;

            for (TrendAnalysisEntity entity : listData) {
                String name = entity.getShengxiao();
                if ("鼠".equals(name)) {
                    shuIndex += 1;
                } else if ("牛".equals(name)) {
                    niuIndex += 1;
                } else if ("虎".equals(name)) {
                    huIndex += 1;
                } else if ("兔".equals(name)) {
                    tuIndex += 1;
                } else if ("龙".equals(name)) {
                    longIndex += 1;
                } else if ("蛇".equals(name)) {
                    sheIndex += 1;
                } else if ("马".equals(name)) {
                    maIndex += 1;
                } else if ("羊".equals(name)) {
                    yangIndex += 1;
                } else if ("猴".equals(name)) {
                    houIndex += 1;
                } else if ("鸡".equals(name)) {
                    jiIndex += 1;
                } else if ("狗".equals(name)) {
                    gouIndex += 1;
                } else if ("猪".equals(name)) {
                    zhuIndex += 1;
                }
            }
            viewHolder.tvShu.setText(shuIndex + "");
            viewHolder.tvNiu.setText(niuIndex + "");
            viewHolder.tvHu.setText(huIndex + "");
            viewHolder.tvTu.setText(tuIndex + "");
            viewHolder.tvLong.setText(longIndex + "");
            viewHolder.tvShe.setText(sheIndex + "");
            viewHolder.tvMa.setText(maIndex + "");
            viewHolder.tvYang.setText(yangIndex + "");
            viewHolder.tvHou.setText(houIndex + "");
            viewHolder.tvJi.setText(jiIndex + "");
            viewHolder.tvGou.setText(gouIndex + "");
            viewHolder.tvZhu.setText(zhuIndex + "");

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

    /**
     * 初始设定UI
     * @param viewHolder
     * @param color
     * @param grayColor
     */
    private void setUI(ViewHolderContent viewHolder, int color, int grayColor) {
        viewHolder.tvShu.setText("--");
        viewHolder.tvShu.setTextColor(grayColor);
        viewHolder.tvNiu.setText("--");
        viewHolder.tvNiu.setTextColor(grayColor);
        viewHolder.tvHu.setText("--");
        viewHolder.tvHu.setTextColor(grayColor);
        viewHolder.tvTu.setText("--");
        viewHolder.tvTu.setTextColor(grayColor);
        viewHolder.tvLong.setText("--");
        viewHolder.tvLong.setTextColor(grayColor);
        viewHolder.tvShe.setText("--");
        viewHolder.tvShe.setTextColor(grayColor);
        viewHolder.tvMa.setText("--");
        viewHolder.tvMa.setTextColor(grayColor);
        viewHolder.tvYang.setText("--");
        viewHolder.tvYang.setTextColor(grayColor);
        viewHolder.tvHou.setText("--");
        viewHolder.tvHou.setTextColor(grayColor);
        viewHolder.tvJi.setText("--");
        viewHolder.tvJi.setTextColor(grayColor);
        viewHolder.tvGou.setText("--");
        viewHolder.tvGou.setTextColor(grayColor);
        viewHolder.tvZhu.setText("--");
        viewHolder.tvZhu.setTextColor(grayColor);
    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNo)
        AppCompatTextView tvNo;
        @BindView(R.id.tvTema)
        AppCompatTextView tvTema;
        @BindView(R.id.tvShu)
        AppCompatTextView tvShu;
        @BindView(R.id.tvNiu)
        AppCompatTextView tvNiu;
        @BindView(R.id.tvHu)
        AppCompatTextView tvHu;
        @BindView(R.id.tvTu)
        AppCompatTextView tvTu;
        @BindView(R.id.tvLong)
        AppCompatTextView tvLong;
        @BindView(R.id.tvShe)
        AppCompatTextView tvShe;
        @BindView(R.id.tvMa)
        AppCompatTextView tvMa;
        @BindView(R.id.tvYang)
        AppCompatTextView tvYang;
        @BindView(R.id.tvHou)
        AppCompatTextView tvHou;
        @BindView(R.id.tvJi)
        AppCompatTextView tvJi;
        @BindView(R.id.tvGou)
        AppCompatTextView tvGou;
        @BindView(R.id.tvZhu)
        AppCompatTextView tvZhu;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {

        @BindView(R.id.tvShu)
        AppCompatTextView tvShu;
        @BindView(R.id.tvNiu)
        AppCompatTextView tvNiu;
        @BindView(R.id.tvHu)
        AppCompatTextView tvHu;
        @BindView(R.id.tvTu)
        AppCompatTextView tvTu;
        @BindView(R.id.tvLong)
        AppCompatTextView tvLong;
        @BindView(R.id.tvShe)
        AppCompatTextView tvShe;
        @BindView(R.id.tvMa)
        AppCompatTextView tvMa;
        @BindView(R.id.tvYang)
        AppCompatTextView tvYang;
        @BindView(R.id.tvHou)
        AppCompatTextView tvHou;
        @BindView(R.id.tvJi)
        AppCompatTextView tvJi;
        @BindView(R.id.tvGou)
        AppCompatTextView tvGou;
        @BindView(R.id.tvZhu)
        AppCompatTextView tvZhu;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
