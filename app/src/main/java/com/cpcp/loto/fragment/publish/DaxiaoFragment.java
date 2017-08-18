package com.cpcp.loto.fragment.publish;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 */

public class DaxiaoFragment extends BaseFragment {
    @BindView(R.id.daxiao_title)
    EditText daxiaoTitle;
    @BindView(R.id.daxiao_count)
    EditText daxiaoCount;
    @BindView(R.id.daxiao_choose1)
    TextView daxiaoChoose1;
    @BindView(R.id.daxiao_choose2)
    TextView daxiaoChoose2;
    @BindView(R.id.daxiao_choose3)
    TextView daxiaoChoose3;
    @BindView(R.id.daxiao_choose4)
    TextView daxiaoChoose4;
    @BindView(R.id.daxiao_choose5)
    TextView daxiaoChoose5;
    @BindView(R.id.daxiao_choose6)
    TextView daxiaoChoose6;
    @BindView(R.id.daxiao_choose7)
    TextView daxiaoChoose7;
    @BindView(R.id.daxiao_da)
    RelativeLayout daxiaoDa;
    @BindView(R.id.daxiao_xiao)
    RelativeLayout daxiaoXiao;
    @BindView(R.id.daxiao_da_txt)
    TextView daxiaoDaTxt;
    @BindView(R.id.daxiao_xiao_txt)
    TextView daxiaoXiaoTxt;

    String chooseRec;
    String chooseCir;

    String type;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_daxiao;
    }

    @Override
    protected void initView() {
//        type = getArguments().getString("type");
        if ("大小".equals(type)) {
            daxiaoDaTxt.setText("大");
            daxiaoXiaoTxt.setText("小");
        }else {
            daxiaoDaTxt.setText("单");
            daxiaoXiaoTxt.setText("双");
        }
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getArguments();
        type = bundle.getString("type");
    }

    @OnClick({R.id.daxiao_choose1, R.id.daxiao_choose2, R.id.daxiao_choose3, R.id.daxiao_choose4,
            R.id.daxiao_choose5, R.id.daxiao_choose6, R.id.daxiao_choose7, R.id.daxiao_da, R.id.daxiao_xiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.daxiao_choose1:
                daxiaoChoose1.setSelected(true);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose1.getText().toString();
                break;
            case R.id.daxiao_choose2:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(true);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose2.getText().toString();

                break;
            case R.id.daxiao_choose3:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(true);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose3.getText().toString();

                break;
            case R.id.daxiao_choose4:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(true);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose4.getText().toString();

                break;
            case R.id.daxiao_choose5:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(true);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose5.getText().toString();

                break;
            case R.id.daxiao_choose6:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(true);
                daxiaoChoose7.setSelected(false);
                chooseRec = daxiaoChoose6.getText().toString();

                break;
            case R.id.daxiao_choose7:
                daxiaoChoose1.setSelected(false);
                daxiaoChoose2.setSelected(false);
                daxiaoChoose3.setSelected(false);
                daxiaoChoose4.setSelected(false);
                daxiaoChoose5.setSelected(false);
                daxiaoChoose6.setSelected(false);
                daxiaoChoose7.setSelected(true);
                chooseRec = daxiaoChoose7.getText().toString();

                break;
            case R.id.daxiao_da:
                daxiaoDa.setSelected(true);
                daxiaoXiao.setSelected(false);
                chooseCir = daxiaoDaTxt.getText().toString();
                break;
            case R.id.daxiao_xiao:
                daxiaoDa.setSelected(false);
                daxiaoXiao.setSelected(true);
                chooseCir = daxiaoDaTxt.getText().toString();
                break;

        }
    }

    @Override
    public void onLazyLoadData() {

    }

    public String getTitle(){
        return daxiaoTitle.getText().toString();
    }

    public String getPoints(){
        return daxiaoCount.getText().toString();
    }

    public String getChooseRec(){
        return chooseRec;
    }

    public String getChooseCir(){
        return chooseCir;
    }
}

