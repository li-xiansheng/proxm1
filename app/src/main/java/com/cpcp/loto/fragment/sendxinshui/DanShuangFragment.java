package com.cpcp.loto.fragment.sendxinshui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.view.NestRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能描述:发布心水，单双
 */

public class DanShuangFragment extends BaseFragment {
    @BindView(R.id.etTitle)
    public AppCompatEditText etTitle;
    @BindView(R.id.etScore)
    public AppCompatEditText etScore;
    @BindView(R.id.rbCode1)
    AppCompatRadioButton rbCode1;
    @BindView(R.id.rbCode2)
    AppCompatRadioButton rbCode2;
    @BindView(R.id.rbCode3)
    AppCompatRadioButton rbCode3;
    @BindView(R.id.lil1)
    LinearLayout lil1;
    @BindView(R.id.rbCode4)
    AppCompatRadioButton rbCode4;
    @BindView(R.id.rbCode5)
    AppCompatRadioButton rbCode5;
    @BindView(R.id.rbCode6)
    AppCompatRadioButton rbCode6;
    @BindView(R.id.lil2)
    LinearLayout lil2;
    @BindView(R.id.rbTema)
    AppCompatRadioButton rbTema;
    @BindView(R.id.radioGroup1)
    public NestRadioGroup radioGroup1;
    @BindView(R.id.rbSingle)
    AppCompatRadioButton rbSingle;
    @BindView(R.id.rbDouble)
    AppCompatRadioButton rbDouble;
    @BindView(R.id.radioGroup2)
    public RadioGroup radioGroup2;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shend_danshuang;
    }

    @Override
    protected void initView() {
        radioGroup1.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void onLazyLoadData() {

    }

}
