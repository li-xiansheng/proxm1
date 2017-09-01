package com.muzikun.lhfsyczl.util;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 封装各种判断点击事件判断
 */

public class ClickEventUtils {

    /**
     * 判断点击区域是否为EditText，如果是返回
     * @param v
     * @param event
     * @return 点击的是EditText返回true，不隐藏键盘;点击的不是EditText返回的是false,隐藏软键盘
     */
    public static boolean isClickEditText(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断点击区域是否为View的区域，如果是返回
     * @param v
     * @param event
     * @return 点击的是View返回true，不隐藏键盘;点击的不是View返回的是false
     */
    public static boolean isClickView(View v, MotionEvent event) {
        if (v != null ) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right && event.getRawY()> top && event.getRawY() < bottom) {
                // 点击的是输入框区域
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
