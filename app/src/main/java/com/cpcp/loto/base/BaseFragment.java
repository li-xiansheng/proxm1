package com.cpcp.loto.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cpcp.loto.util.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能描述：Fragment基类，封装基本流程模板
 */

public abstract class BaseFragment extends Fragment {
    protected String TAG=this.getClass().getSimpleName();
    //ButterKnifeUI注解绑定器
    Unbinder unbinder;
    /**
     * 贴附的activity
     */
    protected BaseActivity mActivity;
    protected Context mContext;
    /**
     * 根布局
     */
    protected View mRootView;
    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否初始化完成--
     */
    protected boolean mIsPrepared;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) getActivity();
        this.mContext=mActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        getIntentData();
        initView();
        initListener();
        mIsPrepared = true;
//        onLazyLoadData();

        return mRootView;//super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 事件监听
     */
    protected void initListener() {
    }

    /**
     *
     * @return 返回当前Fragment的布局文件
     */
    protected abstract int getLayoutResId();
    /**
     * 有数据传递则覆写该方法
     */
    protected void getIntentData() {
    }

    /**
     * 初始化UI
     */
    protected abstract void initView();


    /**
     * 当和Viewpager联合使用时，使用ViewpagerAdapter被调用，否则，只能自己逻辑调用
     * @param isVisibleToUser 当前页面用户是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i(TAG,"可见性setUserVisibleHint"+isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (mIsPrepared && mIsVisible) {
            LogUtils.i(TAG,TAG+"懒加载开始加载数据");
            onLazyLoadData();
            mIsPrepared=false;//只允许首次初始化加载
        }
    }

    /**
     * 仅仅当Fragment嵌套在Viewpager中时执行
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     *
     */
    public abstract void onLazyLoadData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
