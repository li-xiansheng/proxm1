package com.cpcp.loto.activity;

import android.widget.EditText;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

import butterknife.BindView;

/**
 * 功能描述：转换页
 */

public class ChangeActivity extends BaseActivity {

    @BindView(R.id.trans_account)
    EditText transAccount;
    @BindView(R.id.trans_jifen)
    EditText transJifen;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change;
    }

    @Override
    protected void initView() {
        setTitle("转换");
    }

    @Override
    protected void initData() {

    }

}
