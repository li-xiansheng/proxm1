package com.muzikun.lhfsyczl.fragment.xinshui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.LoginActivity;
import com.muzikun.lhfsyczl.adapter.CurrentRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.bean.CurrentRecommendBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

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

public class CurrentFragment extends BasePullRefreshFragment {

    //    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    private List<CurrentRecommendBean> data = new ArrayList<>();
    private CurrentRecyclerAdapter mAdapter;

    private String username;

    private boolean isFirst = true;//是否第一次加载

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {
        super.initView();
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        data = (List<CurrentRecommendBean>) mBaseList;
        mAdapter = new CurrentRecyclerAdapter(mContext, data);
        recyclerView.setAdapter(mAdapter);

        getData();
    }

    @Override
    protected void getData() {
        if (isFirst) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.i(TAG, "首次加载……");
                    mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                    isFirst = false;
//                    fragments.get(0).setUserVisibleHint(true);
                }
            }, 1000);


        } else {
            getCurrentRecommend();
        }
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
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin=sp.getBoolean(UserDB.isLogin,false);
        if (!isLogin){
            ToastUtils.show("未登录,请先登录");
            mActivity.jumpToActivity(LoginActivity.class,true);
            return;
        }
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
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONObject object = new JSONObject(response.getData());
                                    JSONObject danshuangObject = object.optJSONObject("danshuang");
                                    if (danshuangObject != null) {
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "单双";
                                        parseObject(danshuangObject, bean);
                                    }
                                    JSONObject daxiaoObject = object.optJSONObject("daxiao");
                                    if (daxiaoObject != null) {
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "大小";
                                        parseObject(daxiaoObject, bean);
                                    }

                                    JSONObject haomaObject = object.optJSONObject("haoma");
                                    if (haomaObject != null) {
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "号码";
                                        parseObject(haomaObject, bean);

                                    }
                                    JSONObject shengxiaoObject = object.optJSONObject("shengxiao");
                                    if (shengxiaoObject != null) {
                                        CurrentRecommendBean bean = new CurrentRecommendBean();
                                        bean.leixing = "生肖";
                                        parseObject(shengxiaoObject, bean);

                                    }
                                    mAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tvMsg.setText("暂时还没数据...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没数据...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }
                });


    }

    private void parseObject(JSONObject object, CurrentRecommendBean bean) throws JSONException {
        bean.fail = object.getString("fail");
        bean.liangsheng = object.getString("liansheng");
        bean.total = object.getString("total");
        bean.shenglv = object.getString("shenglv");
        bean.id = object.getJSONObject("xinshui").getString("id");
        bean.title = object.getJSONObject("xinshui").getString("title");
        bean.points = object.getJSONObject("xinshui").getString("points");
        bean.desc = object.getJSONObject("xinshui").getString("desc");
        bean.readnum = object.getJSONObject("xinshui").getString("readnum");
        bean.username = object.getJSONObject("xinshui").getString("username");
        bean.is_read = object.getString("is_read");
        data.add(bean);
    }

}
