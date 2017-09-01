package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：弹出框中文本item适配
 */

public class PopItemRecyclerAdapter extends BaseRecycleViewAdapter {

    public PopItemRecyclerAdapter(Context context, List<?> list) {
        super.BaseRecycleViewAdapter(context, list);
    }


    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_text_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            AppCompatTextView text = viewHolder.text;


            if (mListData.get(position) instanceof String) {
                String textStr = (String) mListData.get(position);
                textStr = TextUtils.isEmpty(textStr) ? "" : textStr;
                text.setText(textStr);
            }

        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.text)
        AppCompatTextView text;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
