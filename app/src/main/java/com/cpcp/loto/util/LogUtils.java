package com.cpcp.loto.util;

import android.util.Log;

import com.cpcp.loto.BuildConfig;


/**
*@date 创建时间：2017/4/20
*@description  Log观察工具
*/
public class LogUtils {
	private static final String TAG = "LogUtils";
	// 是否显示Log消息
	private static boolean isShow = BuildConfig.LOG_DEBUG;

	public static void i(String tag, String msg) {
		if (isShow)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isShow)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isShow)
			Log.e(tag, msg);
	}

	public static void all(String msg) {
		if (isShow)
			Log.e("all", msg);
	}

	public static void i(String msg) {
		if (isShow)
			Log.i(TAG, msg);
	}

	public static void w(String msg) {
		if (isShow)
			Log.w(TAG, msg);
	}

	public static void e(String msg) {
		if (isShow)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isShow)
			Log.v(TAG, msg);
	}

	public static void d(String msg) {
		if (isShow)
			Log.d(TAG, msg);
	}

	public static void d(String tag, String msg) {
		if (isShow)
			Log.d(tag, msg);
	}

	public static void w(String tag, String msg, Exception e) {
		if (isShow)
			Log.w(tag, msg, e);
	}

	public static void v(String tag, String msg) {
		if (isShow)
			Log.v(tag, msg);
	}
}
