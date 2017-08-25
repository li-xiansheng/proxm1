package com.cpcp.loto.viewholder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.LiveExpandableListAdapter;
import com.cpcp.loto.entity.OpenLotteryLiveMEntity;

/**
 * Created by lichuanbei on 2016/11/15.
 */

public class ParentMViewHolder extends ParentViewHolder {
    private AppCompatTextView tvQiShu;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ParentMViewHolder(LiveExpandableListAdapter adapter, @NonNull View itemView) {
        super(itemView);
        tvQiShu = (AppCompatTextView) itemView.findViewById(R.id.tvQiShu);

    }

    public void bind(@NonNull final OpenLotteryLiveMEntity parentMEntity, int parentPosition) {
        String qishu = parentMEntity.getQishu();
        qishu = TextUtils.isEmpty(qishu) ? "xxx" : qishu;
        tvQiShu.setText("第" + qishu + "期推荐");
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

    }
}
