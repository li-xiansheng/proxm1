package com.cpcp.loto.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.LiveExpandableListAdapter;
import com.cpcp.loto.entity.OpenLotteryLiveEntity;
import com.cpcp.loto.entity.OpenLotteryLiveMEntity;

import java.util.List;

/**
 * Created by lichuanbei on 2016/11/15.
 */

public class ChildMViewHolder extends ChildViewHolder {
    private AppCompatTextView tvContent;

    public ChildMViewHolder(LiveExpandableListAdapter myAdapter, @NonNull View itemView) {
        super(itemView);
        tvContent = (AppCompatTextView) itemView.findViewById(R.id.tvContent);


    }

    public void bind(final List<OpenLotteryLiveMEntity> parentList, final int parentPosition, @NonNull final OpenLotteryLiveEntity.RecommendBean childMEntity) {
        String catname = childMEntity.getCatname();
        String content = childMEntity.getContent();

        tvContent.setText(catname + "" + content);
    }
}
