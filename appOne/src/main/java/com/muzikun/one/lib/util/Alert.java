package com.muzikun.one.lib.util;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.muzikun.one.R;

/**
 * Created by likun on 16/8/5.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class Alert {
    private static Toast toast = null;

    public static Toast createToast(Context context, String message , int type){
        toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView iconView = new ImageView(context);
        switch (type){
            case 1:
                iconView.setImageResource(R.drawable.success_icon);
                break;
            case 2:
                iconView.setImageResource(R.drawable.error_icon);
                break;
            default:
                iconView.setImageResource(R.drawable.success_icon);
        }
        iconView.setImageResource(R.drawable.success_icon);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 100;
        layoutParams.height = 100;
        iconView.setLayoutParams(layoutParams);
        toastView.addView(iconView,0);
        toastView.setGravity(Gravity.CENTER);
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        return toast;
    }
}
