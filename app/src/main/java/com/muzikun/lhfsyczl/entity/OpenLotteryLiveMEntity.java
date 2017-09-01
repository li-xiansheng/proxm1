package com.muzikun.lhfsyczl.entity;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * 功能描述：开奖直播父列表
 */

public class OpenLotteryLiveMEntity implements Parent<OpenLotteryLiveEntity.RecommendBean> {

    private String qishu;
    private List<OpenLotteryLiveEntity.RecommendBean> partners;

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public List<OpenLotteryLiveEntity.RecommendBean> getPartners() {
        return partners;
    }

    public void setPartners(List<OpenLotteryLiveEntity.RecommendBean> partners) {
        this.partners = partners;
    }


    @Override
    public List<OpenLotteryLiveEntity.RecommendBean> getChildList() {
        return partners;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}
