package com.cpcp.loto.fragment.xinshui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.CurrentRecyclerAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.CurrentRecommendBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class CurrentFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    List<CurrentRecommendBean> data = new ArrayList<>();
    CurrentRecyclerAdapter mAdapter;

    String username;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

//        mAdapter = new CurrentRecyclerAdapter(mContext, data);

        getCurrentRecommend();
    }

    @Override
    public void onLazyLoadData() {

    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getArguments();
        username = bundle.getString("mobile");
    }

    private void getCurrentRecommend() {

        LoadingDialog.showDialog(getActivity());
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("myusername", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getCurrentRecommend(map)
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
                            if (response.getData() != null){
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONObject object = new JSONObject(response.getData());
                                    JSONObject danshuangObject = object.optJSONObject("danshuang");
                                    if (danshuangObject != null){
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "单双";
                                        parseObject(danshuangObject,bean);
                                    }
                                    JSONObject daxiaoObject = object.optJSONObject("daxiao");
                                    if (daxiaoObject != null){
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "大小";
                                        parseObject(daxiaoObject,bean);
                                    }

                                    JSONObject haomaObject = object.optJSONObject("haoma");
                                    if (haomaObject != null){
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "号码";
                                        parseObject(haomaObject,bean);

                                    }
                                    JSONObject shengxiaoObject = object.optJSONObject("shengxiao");
                                    if (shengxiaoObject != null){
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "生肖";
                                        parseObject(shengxiaoObject,bean);

                                    }



                                    Log.i(TAG,"data = "+data.toString());

                                    mAdapter = new CurrentRecyclerAdapter(mContext, data);
                                    recyclerView.setAdapter(mAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                tvMsg.setText("暂时还没数据...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没数据...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }

    private void parseObject(JSONObject object,CurrentRecommendBean bean) throws JSONException {
        bean.fail = object.getString("fail");
        bean.liangsheng = object.getString("liansheng");
        bean.total = object.getString("total");
        bean.shenglv = object.getString("shenglv");
        bean.id = object.getJSONObject("xinshui").getString("id");
        bean.title = object.getJSONObject("xinshui").getString("title");
        bean.points = object.getJSONObject("xinshui").getString("points");
        bean.desc = object.getJSONObject("xinshui").getString("desc");
        bean.readnum = object.getJSONObject("xinshui").getString("readnum");
        data.add(bean);
    }

}
