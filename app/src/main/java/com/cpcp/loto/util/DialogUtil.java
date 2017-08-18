package com.cpcp.loto.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by chentao on 2017/8/18.
 */

public class DialogUtil {

    public static void createDialog(Context context,String title){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("确定",null)
                .create()
                .show();
    }

    public static void createDialog(Context context,String title,String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定",null)
                .create()
                .show();
    }
}
