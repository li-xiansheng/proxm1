package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;
import com.muzikun.lhfsyczl.entity.TrendAnalysisEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：头数走势数据适配
 */

public class HeadTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public HeadTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_head_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_head_trend, parent, false);
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
                    color = mContext.getResources().getColor(R.color.colorPrimaryDarkOne);

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
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    String headValues = tema.substring(0, 1);

                    if ("0".equals(headValues)) {
                        viewHolder.tvZero.setText(tema);
                        viewHolder.tvZero.setTextColor(color);
                    } else if ("1".equals(headValues)) {
                        viewHolder.tvOne.setText(tema);
                        viewHolder.tvOne.setTextColor(color);
                    } else if ("2".equals(headValues)) {
                        viewHolder.tvTwo.setText(tema);
                        viewHolder.tvTwo.setTextColor(color);
                    } else if ("3".equals(headValues)) {
                        viewHolder.tvThree.setText(tema);
                        viewHolder.tvThree.setTextColor(color);
                    } else if ("4".equals(headValues)) {
                        viewHolder.tvFour.setText(tema);
                        viewHolder.tvFour.setTextColor(color);
                    }
                    if ("0".equals(headValues) || "2".equals(headValues) || "4".equals(headValues)) {
                        viewHolder.tvDouble.setText("双");
                        viewHolder.tvDouble.setTextColor(color);
                    } else {
                        viewHolder.tvSingle.setText("单");
                        viewHolder.tvSingle.setTextColor(color);
                    }
                }
            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int zeroIndex = 0;
            int oneIndex = 0;
            int twoIndex = 0;
            int threeIndex = 0;
            int fourIndex = 0;


            for (TrendAnalysisEntity entity : listData) {

                String tema = entity.getTema();
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    String headValues = tema.substring(0, 1);

                    if ("0".equals(headValues)) {
                        zeroIndex += 1;
                    } else if ("1".equals(headValues)) {
                        oneIndex += 1;
                    } else if ("2".equals(headValues)) {
                        twoIndex += 1;
                    } else if ("3".equals(headValues)) {
                        threeIndex += 1;
                    } else if ("4".equals(headValues)) {
                        fourIndex += 1;
                    }


                }
                viewHolder.tvZero.setText(zeroIndex + "");
                viewHolder.tvOne.setText(oneIndex + "");
                viewHolder.tvTwo.setText(twoIndex + "");
                viewHolder.tvThree.setText(threeIndex + "");
                viewHolder.tvFour.setText(fourIndex + "");
                //
                viewHolder.tvSingle.setText((oneIndex + threeIndex) + "");
                viewHolder.tvDouble.setText((zeroIndex + twoIndex + fourIndex) + "");

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
        viewHolder.tvZero.setText("--");
        viewHolder.tvZero.setTextColor(grayColor);
        viewHolder.tvOne.setText("--");
        viewHolder.tvOne.setTextColor(grayColor);
        viewHolder.tvTwo.setText("--");
        viewHolder.tvTwo.setTextColor(grayColor);
        viewHolder.tvThree.setText("--");
        viewHolder.tvThree.setTextColor(grayColor);
        viewHolder.tvFour.setText("--");
        viewHolder.tvFour.setTextColor(grayColor);
        viewHolder.tvDouble.setText("--");
        viewHolder.tvDouble.setTextColor(grayColor);
        viewHolder.tvSingle.setText("--");
        viewHolder.tvSingle.setTextColor(grayColor);

    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNo)
        AppCompatTextView tvNo;
        @BindView(R.id.tvTema)
        AppCompatTextView tvTema;
        @BindView(R.id.tvZero)
        AppCompatTextView tvZero;
        @BindView(R.id.tvOne)
        AppCompatTextView tvOne;
        @BindView(R.id.tvTwo)
        AppCompatTextView tvTwo;
        @BindView(R.id.tvThree)
        AppCompatTextView tvThree;
        @BindView(R.id.tvFour)
        AppCompatTextView tvFour;
        @BindView(R.id.tvDouble)
        AppCompatTextView tvDouble;
        @BindView(R.id.tvSingle)
        AppCompatTextView tvSingle;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.tvZero)
        AppCompatTextView tvZero;
        @BindView(R.id.tvOne)
        AppCompatTextView tvOne;
        @BindView(R.id.tvTwo)
        AppCompatTextView tvTwo;
        @BindView(R.id.tvThree)
        AppCompatTextView tvThree;
        @BindView(R.id.tvFour)
        AppCompatTextView tvFour;
        @BindView(R.id.tvDouble)
        AppCompatTextView tvDouble;
        @BindView(R.id.tvSingle)
        AppCompatTextView tvSingle;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
