package com.muzikun.lhfsyczl.util;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.muzikun.lhfsyczl.MApplication;


/**
*@date 创建时间：2017/4/19
*@description  Toast统一管理类
*/

public class ToastUtils {

    public static Toast toast = null;

    private ToastUtils() {
       /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 自定义Toast，判断是否有显示，有显示
     */
    public static void show(String msg) {
        if (isShow) {
            if (toast == null) {

                toast = Toast.makeText(MApplication.getInstance(),
                        msg + "",
                        Toast.LENGTH_SHORT);
                ViewGroup viewGroup = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) viewGroup.getChildAt(0);
//                messageTextView.setTextSize(18);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }

            toast.show();
        }
    }

    /**
     * 自定义Toast，判断是否有显示，有显示
     */
    public static void show(int msgRes) {
        String msg = MApplication.getInstance().getResources().getString(msgRes);
        if (isShow) {
            if (toast == null) {
                toast = Toast.makeText(MApplication.getInstance(),
                        msg + "",
                        Toast.LENGTH_SHORT);
                ViewGroup viewGroup = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) viewGroup.getChildAt(0);
//                messageTextView.setTextSize(18);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}
