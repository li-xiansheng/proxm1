package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.cpcp.loto.R;
import com.cpcp.loto.entity.OpenLotteryLiveEntity;
import com.cpcp.loto.entity.OpenLotteryLiveMEntity;
import com.cpcp.loto.viewholder.ParentMViewHolder;
import com.cpcp.loto.viewholder.ChildMViewHolder;

import java.util.List;

/**
 * Created by lichuanbei on 2016/11/11.
 * 开奖直播适配器
 */

public class LiveExpandableListAdapter extends ExpandableRecyclerAdapter<OpenLotteryLiveMEntity, OpenLotteryLiveEntity.RecommendBean, ParentMViewHolder, ChildMViewHolder> {

    private final String TAG = this.getClass().getSimpleName();


    private static final int EMPTY_VIEW = 1;
    private static final int PARENT_VEGETARIAN = 2;
    private static final int PARENT_NORMAL = 3;
    private static final int CHILD_VEGETARIAN = 4;
    private static final int CHILD_NORMAL = 5;

    private LayoutInflater mInflater;
    private List<OpenLotteryLiveMEntity> mMEntities;


    public LiveExpandableListAdapter(Context context, @NonNull List<OpenLotteryLiveMEntity> MEntities) {
        super(MEntities);
        mMEntities = MEntities;
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public ParentMViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View convoyView = mInflater.inflate(R.layout.item_open_lottery_live_parent, parentViewGroup, false);
        return new ParentMViewHolder(this, convoyView);
    }

    @UiThread
    @NonNull
    @Override
    public ChildMViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View vehicleView = mInflater.inflate(R.layout.item_open_lottery_live_child, childViewGroup, false);
        return new ChildMViewHolder(this, vehicleView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull ParentMViewHolder parentViewHolder, int parentPosition, @NonNull OpenLotteryLiveMEntity parent) {


        parentViewHolder.bind(parent, parentPosition);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull ChildMViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull OpenLotteryLiveEntity.RecommendBean child) {
        childViewHolder.bind(mMEntities, parentPosition, child);
    }


    @Override
    public int getParentViewType(int parentPosition) {
        return PARENT_NORMAL;

    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return CHILD_NORMAL;
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL;
    }


}
