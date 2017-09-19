package com.muzikun.one.lib.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.muzikun.one.R;

import java.util.UUID;

/**
 * Created by likun on 16/6/23.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class Helper {
    public static void T(Context context , String s){
        Toast.makeText(context,s+"",Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context , int s){
        Toast.makeText(context,String.valueOf(s),Toast.LENGTH_SHORT).show();
    }


    public static void success(){

    }

    public static void error(){

    }


    public static Toast createToast(Context context,String message , int type){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
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

    /** * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值 */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
    /** * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条 */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }
    public static String getPhoneCode(Activity activity){
        String uniqueId = null;
        try {
            final TelephonyManager tm = (TelephonyManager) activity.getBaseContext().getSystemService(activity.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            uniqueId = deviceUuid.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}
