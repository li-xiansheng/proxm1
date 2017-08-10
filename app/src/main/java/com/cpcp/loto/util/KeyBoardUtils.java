package com.cpcp.loto.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 *  软键盘开关工具，
 *  @Override
 *  public boolean dispatchTouchEvent(MotionEvent ev) {
 *  boolean isOpen=KeyBoardUtils.hideSoftInput(this);//
 *  if(isOpen){//软键盘开着
 *  LogUtils.i(TAG,"软键盘开着--执行关闭");
 *  }else{
 *  LogUtils.i(TAG,"软键盘--执行关闭"+ KeyBoardUtils.hideSoftInput(this));
 *  }
 *  return super.dispatchTouchEvent(ev);
 *  }
 *
 * @date 2016-7-15 下午4:46:23
 *
 *
 */
public class KeyBoardUtils {
    public static final String TAG ="KeyBoardUtils" ;

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     * @deprecated 用closeKeyboardByActivity比较好
     */
    public static void closeKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘，通过Activity中的输入框输入框
     *
     * @param activity
     */
    public static void closeKeyboardByActivity(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * Toggle Soft Input
     *
     * @param context
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Show Soft Input
     *
     * @param view
     * @return
     */
    public static boolean showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Show Soft Input
     *
     * @param activity
     * @return
     */
    public static boolean showSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            return imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        }
        return false;
    }

    /**
     * Hide Soft Input
     *
     * @param view
     * @return
     */
    public static boolean hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide Soft Input---隐藏软键盘，
     *
     * @param activity
     * @return 软键盘关着，返回false，软键盘开着,返回true,并立马关闭
     */
    public static boolean hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isSuccessful=imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//            LogUtils.i(TAG,"调用HideSoftInput结果"+isSuccessful);
            return isSuccessful;

        }
        return false;
    }

    /**
     * Judge whether input method is active
     *
     * @param context
     * @return
     */
    public static boolean isActive(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }


}
