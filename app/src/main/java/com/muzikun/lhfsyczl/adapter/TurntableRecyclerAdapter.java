package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseRecycleViewAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：波肖转盘网格适配器
 */

public class TurntableRecyclerAdapter extends BaseRecycleViewAdapter {

    public TurntableRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_turntable, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            if(mListData.get(position) instanceof Map){
                Map<String,Object> map= (Map<String, Object>) mListData.get(position);
                if (map.get("img")!=null){
                    int imgRes= (int) map.get("img");
                    boolean isChecked= (boolean) map.get("isChecked");
                        ((ViewHolder) holder).imageView.setBackgroundResource(imgRes);
                    if (isChecked){
                        ((ViewHolder) holder).imageView.setImageResource(R.drawable.icon_luck_kuang);
                    }else{
                        ((ViewHolder) holder).imageView.setImageResource(0);
                    }
                }else{
                    ((ViewHolder) holder).imageView.setBackgroundResource(0);
                    ((ViewHolder) holder).imageView.setImageResource(0);

                }


            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        AppCompatImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
