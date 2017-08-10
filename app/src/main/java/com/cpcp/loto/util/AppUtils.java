package com.cpcp.loto.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.cpcp.loto.MApplication;


/**
 * App工具类--用于获取app运用信息
 */

public class AppUtils {
    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * @return 获取应用程序名称
     */
    public static String getAppName() {
        try {
            Context context = MApplication.getInstance();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            Context context = MApplication.getInstance();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            Context context = MApplication.getInstance();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取手机系统版本-
     * @return
     */
    public static String getDevicesOS(){
        return  Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT+"_"+Build.MODEL;
    }


    /**
     *
     * 判断Activity是否处于栈顶
     * @return  true在栈顶false不在栈顶
     */
    public static  boolean isMainActivityTop(Activity activity){
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(activity.getClass().getName());
    }
}
