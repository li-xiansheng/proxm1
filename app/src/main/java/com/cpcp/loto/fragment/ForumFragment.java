package com.cpcp.loto.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.ForumDetailActivity;
import com.cpcp.loto.activity.SendArticleActivity;
import com.cpcp.loto.adapter.ForumRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BasePullRefreshFragment;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.ForumEntity;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 功能描述：论坛交流
 */

public class ForumFragment extends BasePullRefreshFragment {

    public static final int REQUEST_CODE =0x111 ;
    @BindView(R.id.rbDefault)
    AppCompatRadioButton rbDefault;
    @BindView(R.id.rbNew)
    AppCompatRadioButton rbNew;
    @BindView(R.id.rbHot)
    AppCompatRadioButton rbHot;
    @BindView(R.id.rbBest)
    AppCompatRadioButton rbBest;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.ivSendArticle)
    SelectedLayerImageView ivSendArticle;
    Unbinder unbinder;


    private List<ForumEntity> mList;
    private boolean isFirst = true;//是否第一次加载
    private ForumRecyclerAdapter mBaseRecycleViewAdapter;

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
    protected void initView() {
        super.initView();
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<ForumEntity>) mBaseList;
        mBaseRecycleViewAdapter = new ForumRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);


        getData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                currentPage = 1;
                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                isFirst = false;
//                getForumInfo();
            }
        });
        mBaseRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ForumEntity forumEntity = mList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", forumEntity.getId());
                ((BaseActivity) mActivity).jumpToActivity(ForumDetailActivity.class, bundle, false);


            }
        });

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
            getForumInfo();
        }
    }


    @Override
    public void onLazyLoadData() {//该页面懒加载无效
        LogUtils.i("懒加载" + TAG);

    }

    /**
     * 获取论坛信息
     */
    private void getForumInfo() {
        int checkedId = radioGroup.getCheckedRadioButtonId();

        String type = "1";
        switch (checkedId) {
            case R.id.rbDefault:
                type = "1";
                break;
            case R.id.rbNew:
                type = "2";
                break;
            case R.id.rbHot:
                type = "3";
                break;
            case R.id.rbBest:
                type = "4";
                break;
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("pageStart", currentPage + "");
        map.put("pageSize", 20 + "");

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getForumInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<ForumEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<ForumEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<ForumEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<ForumEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                mList.addAll(list);
                            } else {
                                ToastUtils.show("没有更多的数据");
                            }
                            mBaseRecycleViewAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (currentPage == 1 && mPullToRefreshRecyclerView != null) {
                            mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        currentPage += 1;
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


    @OnClick({R.id.ivSendArticle})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.ivSendArticle://发表文章
                Intent intent=new Intent(mContext,SendArticleActivity.class);
                startActivityForResult(intent,REQUEST_CODE);

                 break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE&&REQUEST_CODE==REQUEST_CODE){
            getForumInfo();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
