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
 * 功能描述：段位走势数据适配
 */

public class DuanWeiTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public DuanWeiTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_duanwei_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_duanwei_trend, parent, false);
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


                //
                setUI(viewHolder, color, grayColor);
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    int temaValues = Integer.parseInt(tema);

                    if (temaValues >= 0 && temaValues <= 7) {//一段
                        viewHolder.tvOne.setText(tema + "");
                        viewHolder.tvOne.setTextColor(color);
                    } else if (temaValues >= 8 && temaValues <= 14) {//二段
                        viewHolder.tvTwo.setText(tema + "");
                        viewHolder.tvTwo.setTextColor(color);
                    } else if (temaValues >= 15 && temaValues <= 21) {//三段
                        viewHolder.tvThree.setText(tema + "");
                        viewHolder.tvThree.setTextColor(color);
                    } else if (temaValues >= 22 && temaValues <= 28) {//四段
                        viewHolder.tvFour.setText(tema + "");
                        viewHolder.tvFour.setTextColor(color);
                    } else if (temaValues >= 29 && temaValues <= 35) {//五段
                        viewHolder.tvFive.setText(tema + "");
                        viewHolder.tvFive.setTextColor(color);
                    } else if (temaValues >= 36 && temaValues <= 42) {//六段
                        viewHolder.tvSix.setText(tema + "");
                        viewHolder.tvSix.setTextColor(color);
                    } else if (temaValues >= 43 && temaValues <= 49) {//七段
                        viewHolder.tvSeven.setText(tema + "");
                        viewHolder.tvSeven.setTextColor(color);

                    }
                }
            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int oneIndex = 0;
            int twoIndex = 0;
            int threeIndex = 0;
            int fourIndex = 0;
            int fiveIndex = 0;
            int sixIndex = 0;
            int sevenIndex = 0;


            for (TrendAnalysisEntity entity : listData) {

                String tema = entity.getTema();
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    int temaValues = Integer.parseInt(tema);

                    if (temaValues >= 0 && temaValues <= 7) {//一段
                        oneIndex += 1;
                    } else if (temaValues >= 8 && temaValues <= 15) {//二段
                        twoIndex += 1;
                    } else if (temaValues >= 16 && temaValues <= 21) {//三段
                        threeIndex += 1;
                    } else if (temaValues >= 22 && temaValues <= 28) {//四段
                        fourIndex += 1;
                    } else if (temaValues >= 29 && temaValues <= 35) {//五段
                        fiveIndex += 1;
                    } else if (temaValues >= 36 && temaValues <= 42) {//六段
                        sixIndex += 1;
                    } else if (temaValues >= 43 && temaValues <= 49) {//七段
                        sevenIndex += 1;
                    }
                }
                viewHolder.tvOne.setText(oneIndex + "");
                viewHolder.tvTwo.setText(twoIndex + "");
                viewHolder.tvThree.setText(threeIndex + "");
                viewHolder.tvFour.setText(fourIndex + "");
                viewHolder.tvFive.setText(fiveIndex + "");
                viewHolder.tvSix.setText(sixIndex + "");
                viewHolder.tvSeven.setText(sevenIndex + "");
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
    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNo)
        AppCompatTextView tvNo;
        @BindView(R.id.tvTema)
        AppCompatTextView tvTema;
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

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
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

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
