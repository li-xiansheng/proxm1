package com.muzikun.lhfsyczl.listener;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by lichuanbei on 2016/10/31.
 * pop实现接口关闭窗体
 *
 * @author lichuanbei
 *
 */
public class PopupWindowDismissListener implements PopupWindow.OnDismissListener {
    public Activity mActivity;
    public PopupWindowDismissListener(Activity activity){
        this.mActivity=activity;
    }
    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }
}
