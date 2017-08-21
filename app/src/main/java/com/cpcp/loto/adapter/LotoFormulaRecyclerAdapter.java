package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.LotoFormulaActivity;
import com.cpcp.loto.base.BaseRecycleViewAdapter;
import com.cpcp.loto.entity.FormulaEntity;
import com.cpcp.loto.entity.ForumEntity;
import com.cpcp.loto.entity.TrendAnalysisEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：数据适配器
 */

public class LotoFormulaRecyclerAdapter extends BaseRecycleViewAdapter {

    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;


    public LotoFormulaRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_HEAD) {
            View view = mLayoutInflater.inflate(R.layout.item_loto_formula_head, parent, false);
            viewHolder = new ViewHolderHead(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_loto_formula_content, parent, false);
            viewHolder = new ViewHolderContent(view);
        }
        return viewHolder;

    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderContent) {
            ViewHolderContent viewHolder = (ViewHolderContent) holder;
            if (mListData.get(position - 1) instanceof FormulaEntity) {
                FormulaEntity entity = (FormulaEntity) mListData.get(position - 1);
                String excerpt = entity.getPost_excerpt();
                String title = entity.getPost_title();
                excerpt = TextUtils.isEmpty(excerpt) ? "" : excerpt;
                title = TextUtils.isEmpty(title) ? "" : title;

                viewHolder.tvExcerpt.setText(excerpt);
                viewHolder.tvTitle.setText(title);

            }
        } else if (holder instanceof ViewHolderHead) {

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
    }

    /**
     * 内容
     */
    static class ViewHolderContent extends RecyclerView.ViewHolder {
        @BindView(R.id.tvExcerpt)
        AppCompatTextView tvExcerpt;
        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;

        ViewHolderContent(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部
     */
    static class ViewHolderHead extends RecyclerView.ViewHolder {


        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
