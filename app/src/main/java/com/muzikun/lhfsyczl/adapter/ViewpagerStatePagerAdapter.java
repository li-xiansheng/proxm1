package com.muzikun.lhfsyczl.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.List;

/**
 * 功能描述：彩票中需要ViewPager+Fragment，该类作为适配器
 */

public class ViewpagerStatePagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<Fragment> fragments;
    private ViewPager mViewPager;

    public ViewpagerStatePagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
        super(fm);
        this.mContext=context;
        this.fragments=fragmentList;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);//这句话要放在最前面，否则会报错
//        //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
//        int currentItem = mViewPager.getCurrentItem();
//        if (currentItem == currenttab) {
//            return;
//        }
//        imageMove(mViewPager.getCurrentItem());
//        currenttab = mViewPager.getCurrentItem();
    }

//    /**
//     * 移动覆盖层
//     *
//     * @param moveToTab 目标Tab，也就是要移动到的导航选项按钮的位置
//     *                  第一个导航按钮对应0，第二个对应1，以此类推
//     */
//    private void imageMove(int moveToTab) {
//        int startPosition = 0;
//        int movetoPosition = 0;
//
//        startPosition = currenttab * (screenWidth / getCount());
//        movetoPosition = moveToTab * (screenWidth / 4);
//        //平移动画
//        TranslateAnimation translateAnimation = new TranslateAnimation(startPosition, movetoPosition, 0, 0);
//        translateAnimation.setFillAfter(true);
//        translateAnimation.setDuration(200);
//        imageviewOvertab.startAnimation(translateAnimation);
//    }
}
