package com.cpcp.loto.uihelper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 关于沉浸式做法，所有布局文件中使用fitSystemWindows="ture"或者base中处理代码设置
 *
 */
public class StatusBar {
	/**
	 * 设置背景颜色-颜色值
	 * @param activity
	 * @param color
	 * @deprecated 参看setBackgroundResource，如果需要的haul，相同方式扩展
	 */
	public static void setColor(Activity activity, int color) {
		Window win=activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//			ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//			View statusBarView = new View(activity);
//			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
//			statusBarView.setBackgroundColor(color);
//			contentView.addView(statusBarView, lp);
			win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();
			View statusBarView = new View(activity);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBar.getStatusBarHeight(activity));
			statusBarView.setBackgroundColor(color);// 运用主题色
			decorViewGroup.addView(statusBarView, lp);
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//After LOLLIPOP not translucent status bar
			win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//Then call setStatusBarColor.
			win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			win.setStatusBarColor(color);
		}
	}

	/**
	 * 设置背景颜色--资源文件
	 * 
	 * @param activity
	 * @param resource
	 */
	public static void setBackgroundResource(Activity activity, int resource) {
		Window win=activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//			ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//			View statusBarView = new View(activity);
//			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
//			statusBarView.setBackgroundResource(resource);
//			contentView.addView(statusBarView, lp);

			win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();
			View statusBarView = new View(activity);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBar.getStatusBarHeight(activity));
			statusBarView.setBackgroundResource(resource);// 运用主题色
			decorViewGroup.addView(statusBarView, lp);
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//After LOLLIPOP not translucent status bar
			win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//Then call setStatusBarColor.
			win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			win.setStatusBarColor(activity.getResources().getColor(resource));
		}
	}

	/**
	 * 获取状态栏的高度
	 * 
	 * @param context
	 * @return
	 */

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
