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
 * 功能描述：单双走势数据适配
 */

public class DanShuangTrendRecyclerAdapter extends BaseRecycleViewAdapter {


    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public DanShuangTrendRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_danshuang_trend_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_danshuang_trend, parent, false);
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
                viewHolder.tvSingleOrDouble.setTextColor(color);
                viewHolder.tvSingleOrDouble.setText(name);

                //
                setUI(viewHolder, color, grayColor);


                if ("单".equals(name)) {
                    viewHolder.tvSingle.setText("单");
                    viewHolder.tvSingle.setTextColor(color);
                } else {
                    viewHolder.tvDouble.setText("双");
                    viewHolder.tvDouble.setTextColor(color);
                }
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    String head = tema.substring(0, 1);
                    String tail = tema.substring(1, 2);
                    try {
                        int headValues = Integer.parseInt(head);
                        int tailValues = Integer.parseInt(tail);
                        int sum = headValues + tailValues;
                        if (headValues % 2 == 0) {
                            viewHolder.tvHeadDouble.setText("双");
                            viewHolder.tvHeadDouble.setTextColor(color);
                        } else {
                            viewHolder.tvHeadSingle.setText("单");
                            viewHolder.tvHeadSingle.setTextColor(color);
                        }
                        if (sum % 2 == 0) {
                            viewHolder.tvSumDouble.setText("双");
                            viewHolder.tvSumDouble.setTextColor(color);
                        } else {
                            viewHolder.tvSumSingle.setText("单");
                            viewHolder.tvSumSingle.setTextColor(color);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
            }


        } else if (holder instanceof ViewHolderHead) {
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            List<TrendAnalysisEntity> listData = (List<TrendAnalysisEntity>) mListData;
            int singleIndex = 0;
            int doubleIndex = 0;
            int headSingleIndex = 0;
            int headDoubleIndex = 0;
            int sumSingleIndex = 0;
            int sumDoubleIndex = 0;


            for (TrendAnalysisEntity entity : listData) {
                String color = entity.getYanse();
                String name = entity.getDanshuang();
                String tema = entity.getTema();
                if ("单".equals(name)) {
                    singleIndex += 1;
                } else {
                    doubleIndex += 1;
                }
                if (!TextUtils.isEmpty(tema) && tema.length() == 2) {
                    String head = tema.substring(0, 1);
                    String tail = tema.substring(1, 2);
                    try {
                        int headValues = Integer.parseInt(head);
                        int tailValues = Integer.parseInt(tail);
                        int sum = headValues + tailValues;
                        if (headValues % 2 == 0) {
                            headDoubleIndex += 1;
                        } else {
                            headSingleIndex += 1;
                        }
                        if (sum % 2 == 0) {
                            sumDoubleIndex += 1;
                        } else {
                            sumSingleIndex += 1;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            viewHolder.tvSingle.setText(singleIndex + "");
            viewHolder.tvDouble.setText(doubleIndex + "");
            viewHolder.tvHeadSingle.setText(headSingleIndex + "");
            viewHolder.tvHeadDouble.setText(headDoubleIndex + "");
            viewHolder.tvSumSingle.setText(sumSingleIndex + "");
            viewHolder.tvSumDouble.setText(sumDoubleIndex + "");
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
        viewHolder.tvSingle.setText("--");
        viewHolder.tvSingle.setTextColor(grayColor);
        viewHolder.tvDouble.setText("--");
        viewHolder.tvDouble.setTextColor(grayColor);
        viewHolder.tvHeadSingle.setText("--");
        viewHolder.tvHeadSingle.setTextColor(grayColor);

        viewHolder.tvHeadDouble.setText("--");
        viewHolder.tvHeadDouble.setTextColor(grayColor);
        viewHolder.tvSumSingle.setText("--");
        viewHolder.tvSumSingle.setTextColor(grayColor);

        viewHolder.tvSumDouble.setText("--");
        viewHolder.tvSumDouble.setTextColor(grayColor);
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
        @BindView(R.id.tvSingle)
        AppCompatTextView tvSingle;
        @BindView(R.id.tvDouble)
        AppCompatTextView tvDouble;
        @BindView(R.id.tvHeadSingle)
        AppCompatTextView tvHeadSingle;
        @BindView(R.id.tvHeadDouble)
        AppCompatTextView tvHeadDouble;
        @BindView(R.id.tvSumSingle)
        AppCompatTextView tvSumSingle;
        @BindView(R.id.tvSumDouble)
        AppCompatTextView tvSumDouble;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSingle)
        AppCompatTextView tvSingle;
        @BindView(R.id.tvDouble)
        AppCompatTextView tvDouble;
        @BindView(R.id.tvHeadSingle)
        AppCompatTextView tvHeadSingle;
        @BindView(R.id.tvHeadDouble)
        AppCompatTextView tvHeadDouble;
        @BindView(R.id.tvSumSingle)
        AppCompatTextView tvSumSingle;
        @BindView(R.id.tvSumDouble)
        AppCompatTextView tvSumDouble;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
