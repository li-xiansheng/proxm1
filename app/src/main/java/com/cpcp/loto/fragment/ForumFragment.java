package com.cpcp.loto.fragment;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BasePullRefreshFragment;

/**
 * 功能描述：论坛交流
 */

public class ForumFragment extends BasePullRefreshFragment {


    /**
     * 构造Fragment
     *
     * @return 当前Fragment对象
     */
    public static ForumFragment newInstance() {
        ForumFragment fragment = new ForumFragment();
        return fragment;
    }


    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void getData() {

    }


    @Override
    public void onLazyLoadData() {

    }


}
