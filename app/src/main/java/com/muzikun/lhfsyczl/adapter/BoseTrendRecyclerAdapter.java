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
 * 功能描述：波色走势数据适配
 */

public class BoseTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public BoseTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_bose_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_bose_trend, parent, false);
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
                String name = entity.getDanshuang();
                viewHolder.tvNo.setText(no + "");
                viewHolder.tvTema.setText(tema + "");
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
                viewHolder.tvSingleOrDouble.setTextColor(color);
                viewHolder.tvSingleOrDouble.setText(name);

                //
                setUI(viewHolder, color, grayColor);
                if ("lan".equals(yanse)) {
                    viewHolder.tvBlue.setText("蓝");
                    viewHolder.tvBlue.setTextColor(color);
                    if ("单".equals(name)) {
                        viewHolder.tvBlueSingle.setText("单");
                        viewHolder.tvBlueSingle.setTextColor(color);
                    } else {
                        viewHolder.tvBlueDouble.setText("双");
                        viewHolder.tvBlueDouble.setTextColor(color);
                    }
                } else if ("lv".equals(yanse)) {
                    viewHolder.tvGreen.setText("绿");
                    viewHolder.tvGreen.setTextColor(color);
                    if ("单".equals(name)) {
                        viewHolder.tvGreenSingle.setText("单");
                        viewHolder.tvGreenSingle.setTextColor(color);
                    } else {
                        viewHolder.tvGreenDouble.setText("双");
                        viewHolder.tvGreenDouble.setTextColor(color);
                    }
                } else if ("hong".equals(yanse)) {
                    viewHolder.tvRed.setText("红");
                    viewHolder.tvRed.setTextColor(color);
                    if ("单".equals(name)) {
                        viewHolder.tvRedSingle.setText("单");
                        viewHolder.tvRedSingle.setTextColor(color);
                    } else {
                        viewHolder.tvRedDouble.setText("双");
                        viewHolder.tvRedDouble.setTextColor(color);
                    }
                }

            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int redIndex = 0;
            int blueIndex = 0;
            int greenIndex = 0;
            int redSingleIndex = 0;
            int blueSingleIndex = 0;
            int greenSingleIndex = 0;
            int redDoubleIndex = 0;
            int blueDoubleIndex = 0;
            int greenDoubleIndex = 0;


            for (TrendAnalysisEntity entity : listData) {
                String color = entity.getYanse();
                String name = entity.getDanshuang();
                if ("lan".equals(color)) {
                    blueIndex += 1;
                    if ("单".equals(name)) {
                        blueSingleIndex += 1;
                    } else {
                        blueDoubleIndex += 1;
                    }
                } else if ("lv".equals(color)) {
                    greenIndex += 1;
                    if ("单".equals(name)) {
                        greenSingleIndex += 1;
                    } else {
                        greenDoubleIndex += 1;
                    }
                } else if ("hong".equals(color)) {
                    redIndex += 1;
                    if ("单".equals(name)) {
                        redSingleIndex += 1;
                    } else {
                        redDoubleIndex += 1;
                    }
                }
            }
            viewHolder.tvBlue.setText(blueIndex + "");
            viewHolder.tvRed.setText(redIndex + "");
            viewHolder.tvGreen.setText(greenIndex + "");
            viewHolder.tvBlueSingle.setText(blueSingleIndex + "");
            viewHolder.tvGreenSingle.setText(greenSingleIndex + "");
            viewHolder.tvRedSingle.setText(redSingleIndex + "");
            viewHolder.tvBlueDouble.setText(blueDoubleIndex + "");
            viewHolder.tvGreenDouble.setText(greenDoubleIndex + "");
            viewHolder.tvRedDouble.setText(redDoubleIndex + "");

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
        viewHolder.tvRed.setText("--");
        viewHolder.tvRed.setTextColor(grayColor);
        viewHolder.tvGreen.setText("--");
        viewHolder.tvGreen.setTextColor(grayColor);
        viewHolder.tvBlue.setText("--");
        viewHolder.tvBlue.setTextColor(grayColor);

        viewHolder.tvRedSingle.setText("--");
        viewHolder.tvRedSingle.setTextColor(grayColor);
        viewHolder.tvBlueSingle.setText("--");
        viewHolder.tvBlueSingle.setTextColor(grayColor);

        viewHolder.tvGreenSingle.setText("--");
        viewHolder.tvGreenSingle.setTextColor(grayColor);

        viewHolder.tvRedDouble.setText("--");
        viewHolder.tvRedDouble.setTextColor(grayColor);

        viewHolder.tvBlueDouble.setText("--");
        viewHolder.tvBlueDouble.setTextColor(grayColor);

        viewHolder.tvGreenDouble.setText("--");
        viewHolder.tvGreenDouble.setTextColor(grayColor);

    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNo)
        AppCompatTextView tvNo;
        @BindView(R.id.tvTema)
        AppCompatTextView tvTema;
        @BindView(R.id.tvSingleOrDouble)
        AppCompatTextView tvSingleOrDouble;
        @BindView(R.id.tvRed)
        AppCompatTextView tvRed;
        @BindView(R.id.tvGreen)
        AppCompatTextView tvGreen;
        @BindView(R.id.tvBlue)
        AppCompatTextView tvBlue;
        @BindView(R.id.tvRedSingle)
        AppCompatTextView tvRedSingle;
        @BindView(R.id.tvBlueSingle)
        AppCompatTextView tvBlueSingle;
        @BindView(R.id.tvGreenSingle)
        AppCompatTextView tvGreenSingle;
        @BindView(R.id.tvRedDouble)
        AppCompatTextView tvRedDouble;
        @BindView(R.id.tvBlueDouble)
        AppCompatTextView tvBlueDouble;
        @BindView(R.id.tvGreenDouble)
        AppCompatTextView tvGreenDouble;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.tvRed)
        AppCompatTextView tvRed;
        @BindView(R.id.tvGreen)
        AppCompatTextView tvGreen;
        @BindView(R.id.tvBlue)
        AppCompatTextView tvBlue;
        @BindView(R.id.tvRedSingle)
        AppCompatTextView tvRedSingle;
        @BindView(R.id.tvBlueSingle)
        AppCompatTextView tvBlueSingle;
        @BindView(R.id.tvGreenSingle)
        AppCompatTextView tvGreenSingle;
        @BindView(R.id.tvRedDouble)
        AppCompatTextView tvRedDouble;
        @BindView(R.id.tvBlueDouble)
        AppCompatTextView tvBlueDouble;
        @BindView(R.id.tvGreenDouble)
        AppCompatTextView tvGreenDouble;


        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
