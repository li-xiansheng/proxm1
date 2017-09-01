package com.muzikun.lhfsyczl.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * 
 * @date 2016-5-26 上午11:04:45
 *
 * 工具类，除用户表以外，可记录通过该表进行操作新建不同的SharedPreferences
 */
public class SPUtil {
	private Context context;
	private SharedPreferences sp;
	private Editor editor;

	/**
	 * 该构造方法只定义默认sp表comom
	 *
	 * @param context
	 */
	public SPUtil(Context context) {
		this.context = context;
		sp = this.context.getSharedPreferences("common", Context.MODE_APPEND);
		editor = sp.edit();
	}

	/**
	 * 构造方法。
	 * 
	 * @param context
	 * @param kvName
	 *            键值表名称。
	 */
	public SPUtil(Context context, String kvName) {
		sp = context.getApplicationContext().getSharedPreferences(kvName, Context.MODE_APPEND);
		editor = sp.edit();
	}

	public void getInstance(Context context, String filename) {
		this.context = context;
		sp = context.getSharedPreferences(filename, Context.MODE_APPEND);
		editor = sp.edit();
	}

	public void putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key, Boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	public void putString(String key, String value) {
		if (key == null) {
			return;
		}
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}
	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}
	public float getFloat(String key, float defValue) {
		return sp.getFloat(key, defValue);
	}
	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public Map<String, ?> getAll() {
		return sp.getAll();
	}

	/**
	 * 清理改sp文件所有数据
	 */
	public void clearData() {
		sp.edit().clear().commit();
	}
}
