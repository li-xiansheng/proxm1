package com.cpcp.loto.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能描述：
 * 图片处理工具类
 */

public class ImageUtil {

    /**
     * 通过矩证变换 设置图标覆盖色彩
     *
     * @param imageView
     * @param brightness 可为0灰色，1彩色原图
     */
    public static void changeLight(ImageView imageView, int brightness) {//ImageView imageview,
        ColorMatrix matrix = new ColorMatrix();
//        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
//                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        matrix.setSaturation(brightness);
        imageView.setColorFilter(new ColorMatrixColorFilter(matrix));
    }


    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic = getDiskFileDir(context,  System.currentTimeMillis() + ".png");
        try {
//            if (!filePic.exists()) {
//                filePic.getParentFile().mkdirs();
//                filePic.createNewFile();
//            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 获取运用程序的缓存路径，支持api9以上
     *
     * @param context
     * @return
     */

    public static File getDiskFileDir(Context context, String cacheFile) {
        String cachePath;
        // 外部储存卡在，或者不可被移除则返回外部路径加缓存类型文件名
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getFilesDir().getPath();
        }
        return new File(cachePath + File.separator + cacheFile);
    }
}
