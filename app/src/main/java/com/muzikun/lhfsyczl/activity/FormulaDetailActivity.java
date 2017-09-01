package com.muzikun.lhfsyczl.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.view.htmltextview.HtmlTextView;

import butterknife.BindView;

/**
 * 功能描述：公式详情
 */

public class FormulaDetailActivity extends BaseActivity {

    @BindView(R.id.tvContent)
    HtmlTextView tvContent;
    private String content;

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            content = intent.getExtras().getString("content");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_formula_detail;
    }

    @Override
    protected void initView() {
        setTitle("公式详情");

        content = TextUtils.isEmpty(content) ? "" : content;
        tvContent.setHtmlFromString(content.replace("\n", "<br />"), false);
        tvContent.setTextColor(mContext.getResources().getColor(R.color.blackText));
    }

    @Override
    protected void initData() {

    }


}
