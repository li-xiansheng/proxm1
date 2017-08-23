package com.cpcp.loto.activity;

import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

import butterknife.BindView;

public class MianzeActivity extends BaseActivity {

    String str;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mianze;
    }

    @Override
    protected void initView() {
        setTitle("免责申明");


        text.setText(Html.fromHtml(str));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = getIntent();
        if (intent != null) {
            str = intent.getStringExtra("str");
        }
    }

}
