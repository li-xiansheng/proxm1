package com.muzikun.one.util;

import android.content.Context;
import android.content.Intent;

/**
 * Description: JumpUtil
 * Creator: yxc
 * date: 2016/9/21 14:46
 */
public class JumpUtil {

//    public static void go2VideoInfoActivity(Context context, MyVideoInfo videoInfo) {
//        Intent intent = new Intent(context, VideoInfoActivity.class);
//        intent.putExtra("videoInfo", videoInfo);
//        context.startActivity(intent);
//    }



    public static void jump(Context a, Class<?> clazz) {
        Intent intent = new Intent(a, clazz);
        a.startActivity(intent);
    }
}
