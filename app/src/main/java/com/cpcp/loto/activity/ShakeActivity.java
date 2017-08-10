package com.cpcp.loto.activity;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

/**
 * 功能描述：摇一摇
 */

public class ShakeActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shake;
    }

    @Override
    protected void initView() {
        setTitle("摇一摇");
    }

    @Override
    protected void initData() {

    }
}
