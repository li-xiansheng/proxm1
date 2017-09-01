package com.muzikun.lhfsyczl.uihelper;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.util.DisplayUtil;


/**
 * @date 创建时间：2016/12/5.
 * @description Loading进度等待帮助类
 */

public class LoadingDialog {
    public static AlertDialog mAlertDialog;


    /**
     * 得到自定义的progressDialog
     *
     * @param activity
     * @param msg
     * @return
     */
    public static AlertDialog createLoadingDialog(Activity activity, String msg) {
        if (activity == null) {
            return null;
        }
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            return mAlertDialog;
        }
        // 首先得到整个View
        View view = LayoutInflater.from(activity).inflate(R.layout.view_loading_dialog, null);

        // 页面中显示文本
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);

        // 显示文本
        tvMessage.setText(msg);
        mAlertDialog = new AlertDialog.Builder(activity).create();
        if (activity != null && !activity.isFinishing()) {
            mAlertDialog.show();
            WindowManager.LayoutParams params = mAlertDialog.getWindow().getAttributes();
            params.height = DisplayUtil.dip2px(activity, 100);
            params.width = DisplayUtil.dip2px(activity, 100);
            mAlertDialog.getWindow().setAttributes(params);
        }
        mAlertDialog.setCancelable(true);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setContentView(view);
        return mAlertDialog;
    }

    /**
     * 显示Dialog
     */
    public static void showDialog(Activity activity) {
        //每次重建靠谱
        if (activity != null) {
            mAlertDialog = LoadingDialog.createLoadingDialog(activity, "正在加载中...");
        }


    }

    /**
     * 显示Dialog
     */
    public static void showDialog(Activity activity, int drawableRes, String msg) {
        //每次重建靠谱
        if (activity != null) {
            mAlertDialog = LoadingDialog.createLoadingDialog(activity, msg);
        }


    }

    /**
     * 关闭Dialog
     */
    public static void closeDialog(Activity activity) {
        if (mAlertDialog != null) {
            if (activity != null && !activity.isFinishing() && mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
            }
            mAlertDialog = null;
        }
    }

}
