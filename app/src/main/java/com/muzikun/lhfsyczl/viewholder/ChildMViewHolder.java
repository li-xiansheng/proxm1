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
