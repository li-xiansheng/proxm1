package com.muzikun.lhfsyczl.uihelper;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 功能描述：pop窗体弹出，消失时背景
 */

public class PopupDismissListener implements PopupWindow.OnDismissListener{
    private Activity mActivity;
    public PopupDismissListener(Activity activity) {
        this.mActivity=activity;
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }
}