package com.cpcp.loto.fragment.bet;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.LoginActivity;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.ShengxiaoBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.SortUtil;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：生肖投注
 */

public class ShengxiaoBetFragment extends BaseFragment {
    @BindView(R.id.qishu)
    TextView qishu;
    @BindView(R.id.remen)
    TextView remen;
    @BindView(R.id.rb_shu)
    RadioButton rbShu;
    @BindView(R.id.progress_shu)
    MagicProgressBar progressShu;
    @BindView(R.id.txt_shu)
    TextView txtShu;
    @BindView(R.id.ll_shu)
    LinearLayout llShu;
    @BindView(R.id.rb_niu)
    RadioButton rbNiu;
    @BindView(R.id.progress_niu)
    MagicProgressBar progressNiu;
    @BindView(R.id.txt_niu)
    TextView txtNiu;
    @BindView(R.id.ll_niu)
    LinearLayout llNiu;
    @BindView(R.id.rb_hu)
    RadioButton rbHu;
    @BindView(R.id.progress_hu)
    MagicProgressBar progressHu;
    @BindView(R.id.txt_hu)
    TextView txtHu;
    @BindView(R.id.ll_hu)
    LinearLayout llHu;
    @BindView(R.id.rb_tu)
    RadioButton rbTu;
    @BindView(R.id.progress_tu)
    MagicProgressBar progressTu;
    @BindView(R.id.txt_tu)
    TextView txtTu;
    @BindView(R.id.ll_tu)
    LinearLayout llTu;
    @BindView(R.id.rb_long)
    RadioButton rbLong;
    @BindView(R.id.progress_long)
    MagicProgressBar progressLong;
    @BindView(R.id.txt_long)
    TextView txtLong;
    @BindView(R.id.ll_long)
    LinearLayout llLong;
    @BindView(R.id.rb_she)
    RadioButton rbShe;
    @BindView(R.id.progress_she)
    MagicProgressBar progressShe;
    @BindView(R.id.txt_she)
    TextView txtShe;
    @BindView(R.id.ll_she)
    LinearLayout llShe;
    @BindView(R.id.rb_ma)
    RadioButton rbMa;
    @BindView(R.id.progress_ma)
    MagicProgressBar progressMa;
    @BindView(R.id.txt_ma)
    TextView txtMa;
    @BindView(R.id.ll_ma)
    LinearLayout llMa;
    @BindView(R.id.rb_yang)
    RadioButton rbYang;
    @BindView(R.id.progress_yang)
    MagicProgressBar progressYang;
    @BindView(R.id.txt_yang)
    TextView txtYang;
    @BindView(R.id.ll_yang)
    LinearLayout llYang;
    @BindView(R.id.rb_hou)
    RadioButton rbHou;
    @BindView(R.id.progress_hou)
    MagicProgressBar progressHou;
    @BindView(R.id.txt_hou)
    TextView txtHou;
    @BindView(R.id.ll_hou)
    LinearLayout llHou;
    @BindView(R.id.rb_ji)
    RadioButton rbJi;
    @BindView(R.id.progress_ji)
    MagicProgressBar progressJi;
    @BindView(R.id.txt_ji)
    TextView txtJi;
    @BindView(R.id.ll_ji)
    LinearLayout llJi;
    @BindView(R.id.rb_gou)
    RadioButton rbGou;
    @BindView(R.id.progress_gou)
    MagicProgressBar progressGou;
    @BindView(R.id.txt_gou)
    TextView txtGou;
    @BindView(R.id.ll_gou)
    LinearLayout llGou;
    @BindView(R.id.rb_zhu)
    RadioButton rbZhu;
    @BindView(R.id.progress_zhu)
    MagicProgressBar progressZhu;
    @BindView(R.id.txt_zhu)
    TextView txtZhu;
    @BindView(R.id.ll_zhu)
    LinearLayout llZhu;
    @BindView(R.id.toupiao)
    RelativeLayout toupiao;


    List<String> chooseList = new ArrayList<>();
    List<RadioButton> buttons = new ArrayList<>();
    List<RadioButton> allButtons = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shengxiao_bet;
    }

    @Override
    protected void initView() {


    }

    @OnClick({R.id.ll_shu, R.id.ll_niu, R.id.ll_hu, R.id.ll_tu, R.id.ll_long, R.id.ll_she, R.id.ll_ma, R.id.ll_yang,
            R.id.ll_hou, R.id.ll_ji, R.id.ll_gou, R.id.ll_zhu, R.id.toupiao})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_shu:
                if (buttons.size() < 3) {
                    buttons.add(rbShu);
                    chooseList.add("鼠");
                } else {
                    buttons.remove(0);
                    buttons.add(rbShu);
                    chooseList.add("鼠");
                }
                changeRbState();
                break;
            case R.id.ll_niu:
                if (buttons.size() < 3) {
                    buttons.add(rbNiu);
                    chooseList.add("牛");
                } else {
                    buttons.remove(0);
                    buttons.add(rbNiu);
                    chooseList.add("牛");
                }
                changeRbState();
                break;
            case R.id.ll_hu:
                if (buttons.size() < 3) {
                    buttons.add(rbHu);
                    chooseList.add("虎");
                } else {
                    buttons.remove(0);
                    buttons.add(rbHu);
                    chooseList.add("虎");
                }
                changeRbState();
                break;
            case R.id.ll_tu:
                if (buttons.size() < 3) {
                    buttons.add(rbTu);
                    chooseList.add("兔");
                } else {
                    buttons.remove(0);
                    buttons.add(rbTu);
                    chooseList.add("兔");
                }
                changeRbState();
                break;
            case R.id.ll_long:
                if (buttons.size() < 3) {
                    buttons.add(rbLong);
                    chooseList.add("龙");
                } else {
                    buttons.remove(0);
                    buttons.add(rbLong);
                    chooseList.add("龙");
                }
                changeRbState();
                break;
            case R.id.ll_she:
                if (buttons.size() < 3) {
                    buttons.add(rbShe);
                    chooseList.add("蛇");
                } else {
                    buttons.remove(0);
                    buttons.add(rbShe);
                    chooseList.add("蛇");
                }
                changeRbState();
                break;
            case R.id.ll_ma:
                if (buttons.size() < 3) {
                    buttons.add(rbMa);
                    chooseList.add("马");
                } else {
                    buttons.remove(0);
                    buttons.add(rbMa);
                    chooseList.add("马");
                }
                changeRbState();
                break;
            case R.id.ll_yang:
                if (buttons.size() < 3) {
                    buttons.add(rbYang);
                    chooseList.add("羊");
                } else {
                    buttons.remove(0);
                    buttons.add(rbYang);
                    chooseList.add("羊");
                }
                changeRbState();
                break;
            case R.id.ll_hou:
                if (buttons.size() < 3) {
                    buttons.add(rbHou);
                    chooseList.add("猴");
                } else {
                    buttons.remove(0);
                    buttons.add(rbHou);
                    chooseList.add("猴");
                }
                changeRbState();
                break;
            case R.id.ll_ji:
                if (buttons.size() < 3) {
                    buttons.add(rbJi);
                    chooseList.add("鸡");
                } else {
                    buttons.remove(0);
                    buttons.add(rbJi);
                    chooseList.add("鸡");
                }
                changeRbState();
                break;
            case R.id.ll_gou:
                if (buttons.size() < 3) {
                    buttons.add(rbGou);
                    chooseList.add("狗");
                } else {
                    buttons.remove(0);
                    buttons.add(rbGou);
                    chooseList.add("狗");
                }
                changeRbState();
                break;
            case R.id.ll_zhu:
                if (buttons.size() < 3) {
                    buttons.add(rbZhu);
                    chooseList.add("猪");
                } else {
                    buttons.remove(0);
                    buttons.add(rbZhu);
                    chooseList.add("猪");
                }
                changeRbState();
                break;
            case R.id.toupiao:
                putVote();
                break;

        }

    }

    @Override
    public void onLazyLoadData() {
        getVote();
    }

    private void changeRbState() {

        allButtons.clear();
        initAllButtons();

        for (int i = 0; i < allButtons.size(); i++) {
            for (int j = 0; j < buttons.size(); j++) {
                if (allButtons.get(i) == buttons.get(j)) {
                    allButtons.remove(i);
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setChecked(true);
        }
        for (int i = 0; i < allButtons.size(); i++) {
            allButtons.get(i).setChecked(false);
        }
    }

    private void initAllButtons() {
        allButtons.add(rbShu);
        allButtons.add(rbNiu);
        allButtons.add(rbHu);
        allButtons.add(rbTu);
        allButtons.add(rbLong);
        allButtons.add(rbShe);
        allButtons.add(rbMa);
        allButtons.add(rbYang);
        allButtons.add(rbHou);
        allButtons.add(rbJi);
        allButtons.add(rbGou);
        allButtons.add(rbZhu);
    }

    private void putVote() {

        if (chooseList.size() == 0) {
            DialogUtil.createDialog(mContext,"您还未选择生肖..");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<chooseList.size();i++){
            builder.append(chooseList.get(i)+",");
        }
        String choose = builder.deleteCharAt(builder.length()-1).toString();
        LogUtils.i(TAG, "choose = " + choose);
        LoadingDialog.showDialog(getActivity());
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin=sp.getBoolean(UserDB.isLogin,false);
        if(!isLogin){
            ((BaseActivity)mActivity).jumpToActivity(LoginActivity.class,false);
            return;
        }
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("username", tel);
        map.put("poll", choose);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.putCaiMinVote(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(getActivity());
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            DialogUtil.createDialog(mContext, "投票成功");
                        } else {
                            DialogUtil.createDialog(mContext, response.getErrmsg());
                        }
                    }
                });
    }

    private void getVote() {
        LoadingDialog.showDialog(getActivity());
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getCaiMinVote()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(getActivity());
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                try {
                                    ShengxiaoBean shengxiaoBean = new ShengxiaoBean();
                                    JSONObject object = new JSONObject(response.getData());
                                    JSONObject dataObject = object.optJSONObject("data");
                                    shengxiaoBean.qishu = object.getString("shijian");

                                    JSONObject boseObject = dataObject.optJSONObject("shengxiao");
                                    shengxiaoBean.shu = boseObject.getInt("shu");
                                    shengxiaoBean.niu = boseObject.getInt("niu");
                                    shengxiaoBean.hu = boseObject.getInt("hu");
                                    shengxiaoBean.tu = boseObject.getInt("tu");
                                    shengxiaoBean.dragon = boseObject.getInt("long");
                                    shengxiaoBean.she = boseObject.getInt("she");
                                    shengxiaoBean.ma = boseObject.getInt("ma");
                                    shengxiaoBean.yang = boseObject.getInt("yang");
                                    shengxiaoBean.hou = boseObject.getInt("hou");
                                    shengxiaoBean.ma = boseObject.getInt("ji");
                                    shengxiaoBean.gou = boseObject.getInt("gou");
                                    shengxiaoBean.zhu = boseObject.getInt("zhu");

                                    refreshView(shengxiaoBean);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private void refreshView(ShengxiaoBean shengxiaoBean) {

        txtShu.setText(shengxiaoBean.shu + "%");
        txtNiu.setText(shengxiaoBean.niu + "%");
        txtHu.setText(shengxiaoBean.hu + "%");
        txtTu.setText(shengxiaoBean.tu + "%");
        txtLong.setText(shengxiaoBean.dragon + "%");
        txtShe.setText(shengxiaoBean.she + "%");
        txtMa.setText(shengxiaoBean.ma + "%");
        txtYang.setText(shengxiaoBean.yang + "%");
        txtHou.setText(shengxiaoBean.hou + "%");
        txtJi.setText(shengxiaoBean.ji + "%");
        txtGou.setText(shengxiaoBean.gou + "%");
        txtZhu.setText(shengxiaoBean.zhu + "%");

        progressShu.setSmoothPercent((float) shengxiaoBean.shu / 100, 500);
        progressNiu.setSmoothPercent((float) shengxiaoBean.niu / 100, 500);
        progressHu.setSmoothPercent((float) shengxiaoBean.hu / 100, 500);
        progressTu.setSmoothPercent((float) shengxiaoBean.tu / 100, 500);
        progressLong.setSmoothPercent((float) shengxiaoBean.dragon / 100, 500);
        progressShe.setSmoothPercent((float) shengxiaoBean.she / 100, 500);
        progressMa.setSmoothPercent((float) shengxiaoBean.ma / 100, 500);
        progressYang.setSmoothPercent((float) shengxiaoBean.yang / 100, 500);
        progressHou.setSmoothPercent((float) shengxiaoBean.hou / 100, 500);
        progressJi.setSmoothPercent((float) shengxiaoBean.ji / 100, 500);
        progressGou.setSmoothPercent((float) shengxiaoBean.gou / 100, 500);
        progressZhu.setSmoothPercent((float) shengxiaoBean.zhu / 100, 500);


        qishu.setText(shengxiaoBean.qishu);

        Map<String, Integer> map = new HashMap<>();
        map.put("鼠", shengxiaoBean.shu);
        map.put("牛", shengxiaoBean.niu);
        map.put("虎", shengxiaoBean.hu);
        map.put("兔", shengxiaoBean.tu);
        map.put("龙", shengxiaoBean.dragon);
        map.put("蛇", shengxiaoBean.she);
        map.put("马", shengxiaoBean.ma);
        map.put("羊", shengxiaoBean.yang);
        map.put("猴", shengxiaoBean.hou);
        map.put("鸡", shengxiaoBean.ji);
        map.put("狗", shengxiaoBean.gou);
        map.put("猪", shengxiaoBean.zhu);

        Map<String, Integer> sortMap = SortUtil.sortMapByValue(map);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            if (entry.getValue() > 0){
                builder.append(entry.getKey() + "、");
                i++;
                System.out.println("key= " + entry.getKey());
                if (i >= 3) {
                    break;
                }
            }else {
                break;
            }
        }
        if (TextUtils.isEmpty(builder)){
            remen.setText("本期热门: 暂无");
        }else {
            remen.setText("本期热门: " + builder.deleteCharAt(builder.length() - 1).toString());
        }


    }
}
