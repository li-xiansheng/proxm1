package com.muzikun.lhfsyczl.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.LiveExpandableListAdapter;
import com.muzikun.lhfsyczl.entity.OpenLotteryLiveEntity;
import com.muzikun.lhfsyczl.entity.OpenLotteryLiveMEntity;

import java.util.List;

/**
 * Created by lichuanbei on 2016/11/15.
 */

public class ChildMViewHolder extends ChildViewHolder {
    private AppCompatTextView tvContent;
    private AppCompatTextView tvContent1;

    public ChildMViewHolder(LiveExpandableListAdapter myAdapter, @NonNull View itemView) {
        super(itemView);
        tvContent = (AppCompatTextView) itemView.findViewById(R.id.tvContent);
        tvContent1 = (AppCompatTextView) itemView.findViewById(R.id.tvContent1);


    }

    public void bind(final List<OpenLotteryLiveMEntity> parentList, final int parentPosition, @NonNull final OpenLotteryLiveEntity.RecommendBean childMEntity) {

        String combine = childMEntity.getCombine();
        String combine1=childMEntity.getCombine1();
        //如果合并项有第二个，这显示竖线分割区
        if (combine1!=null){
            tvContent1.setVisibility(View.VISIBLE);
            tvContent.setText(combine);
            tvContent1.setText(combine1);
        }else{
            if (combine.length()>9){
                tvContent1.setVisibility(View.GONE);
            }else{
                tvContent1.setVisibility(View.VISIBLE);
            }

            tvContent.setText(combine);
            tvContent1.setText("");
        }


    }
}
