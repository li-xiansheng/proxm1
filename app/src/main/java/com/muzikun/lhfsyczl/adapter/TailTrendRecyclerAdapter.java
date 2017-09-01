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
 * 功能描述：尾数走势数据适配
 */

public class TailTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public TailTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_tail_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_tail_trend, parent, false);
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
                String daxiao = entity.getDaxiao();
                String wei = entity.getWei();

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


                if ("0".equals(wei)) {
                    viewHolder.tvZero.setText(tema);
                    viewHolder.tvZero.setTextColor(color);
                } else if ("1".equals(wei)) {
                    viewHolder.tvOne.setText(tema);
                    viewHolder.tvOne.setTextColor(color);
                } else if ("2".equals(wei)) {
                    viewHolder.tvTwo.setText(tema);
                    viewHolder.tvTwo.setTextColor(color);
                } else if ("3".equals(wei)) {
                    viewHolder.tvThree.setText(tema);
                    viewHolder.tvThree.setTextColor(color);
                } else if ("4".equals(wei)) {
                    viewHolder.tvFour.setText(tema);
                    viewHolder.tvFour.setTextColor(color);
                } else if ("5".equals(wei)) {
                    viewHolder.tvFive.setText(tema);
                    viewHolder.tvFive.setTextColor(color);
                } else if ("6".equals(wei)) {
                    viewHolder.tvSix.setText(tema);
                    viewHolder.tvSix.setTextColor(color);
                } else if ("7".equals(wei)) {
                    viewHolder.tvSeven.setText(tema);
                    viewHolder.tvSeven.setTextColor(color);
                } else if ("8".equals(wei)) {
                    viewHolder.tvEight.setText(tema);
                    viewHolder.tvEight.setTextColor(color);
                } else if ("9".equals(wei)) {
                    viewHolder.tvNine.setText(tema);
                    viewHolder.tvNine.setTextColor(color);
                }
                if ("大".equals(daxiao)) {
                    viewHolder.tvBig.setText(daxiao);
                    viewHolder.tvBig.setTextColor(color);
                } else if ("小".equals(daxiao)) {
                    viewHolder.tvLittle.setText(daxiao);
                    viewHolder.tvLittle.setTextColor(color);
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
            int fiveIndex = 0;
            int sixIndex = 0;
            int sevenIndex = 0;
            int eightIndex = 0;
            int nineIndex = 0;
            int littleIndex = 0;
            int bigIndex = 0;


            for (TrendAnalysisEntity entity : listData) {
                String wei = entity.getWei();
                String daxiao = entity.getDaxiao();
                if ("0".equals(wei)) {
                    zeroIndex += 1;
                } else if ("1".equals(wei)) {
                    oneIndex += 1;
                } else if ("2".equals(wei)) {
                    twoIndex += 1;
                } else if ("3".equals(wei)) {
                    threeIndex += 1;
                } else if ("4".equals(wei)) {
                    fourIndex += 1;
                } else if ("5".equals(wei)) {
                    fiveIndex += 1;
                } else if ("6".equals(wei)) {
                    sixIndex += 1;
                } else if ("7".equals(wei)) {
                    sevenIndex += 1;
                } else if ("8".equals(wei)) {
                    eightIndex += 1;
                } else if ("9".equals(wei)) {
                    nineIndex += 1;
                }
                if ("大".equals(daxiao)) {
                    bigIndex += 1;
                } else if ("小".equals(daxiao)) {
                    littleIndex += 1;
                }
                viewHolder.tvZero.setText(zeroIndex + "");
                viewHolder.tvOne.setText(oneIndex + "");
                viewHolder.tvTwo.setText(twoIndex + "");
                viewHolder.tvThree.setText(threeIndex + "");
                viewHolder.tvFour.setText(fourIndex + "");
                viewHolder.tvFive.setText(fiveIndex + "");
                viewHolder.tvSix.setText(sixIndex + "");
                viewHolder.tvSeven.setText(sevenIndex + "");
                viewHolder.tvEight.setText(eightIndex + "");
                viewHolder.tvNine.setText(nineIndex + "");
                //
                viewHolder.tvLittle.setText(littleIndex + "");
                viewHolder.tvBig.setText(bigIndex + "");

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
        viewHolder.tvFive.setText("--");
        viewHolder.tvFive.setTextColor(grayColor);
        viewHolder.tvSix.setText("--");
        viewHolder.tvSix.setTextColor(grayColor);
        viewHolder.tvSeven.setText("--");
        viewHolder.tvSeven.setTextColor(grayColor);
        viewHolder.tvEight.setText("--");
        viewHolder.tvEight.setTextColor(grayColor);
        viewHolder.tvNine.setText("--");
        viewHolder.tvNine.setTextColor(grayColor);
        viewHolder.tvLittle.setText("--");
        viewHolder.tvLittle.setTextColor(grayColor);
        viewHolder.tvBig.setText("--");
        viewHolder.tvBig.setTextColor(grayColor);
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
        @BindView(R.id.tvFive)
        AppCompatTextView tvFive;
        @BindView(R.id.tvSix)
        AppCompatTextView tvSix;
        @BindView(R.id.tvSeven)
        AppCompatTextView tvSeven;
        @BindView(R.id.tvEight)
        AppCompatTextView tvEight;
        @BindView(R.id.tvNine)
        AppCompatTextView tvNine;
        @BindView(R.id.tvLittle)
        AppCompatTextView tvLittle;
        @BindView(R.id.tvBig)
        AppCompatTextView tvBig;

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
        @BindView(R.id.tvFive)
        AppCompatTextView tvFive;
        @BindView(R.id.tvSix)
        AppCompatTextView tvSix;
        @BindView(R.id.tvSeven)
        AppCompatTextView tvSeven;
        @BindView(R.id.tvEight)
        AppCompatTextView tvEight;
        @BindView(R.id.tvNine)
        AppCompatTextView tvNine;
        @BindView(R.id.tvLittle)
        AppCompatTextView tvLittle;
        @BindView(R.id.tvBig)
        AppCompatTextView tvBig;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
