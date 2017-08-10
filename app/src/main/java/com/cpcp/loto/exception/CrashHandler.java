package com.cpcp.loto.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.cpcp.loto.util.LogUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局未捕获异常获取帮助类
 *
 * @author Administrator
 */
public class CrashHandler implements UncaughtExceptionHandler {

    // 调试
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    // 路径名
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/" + "cpcp/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".log";
    private File mfile;
    // 单例
    private static CrashHandler sInstance = new CrashHandler();
    private UncaughtExceptionHandler mDefaExceptionHandler;
    private Context mContext;

    //
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 当程序有未捕获的异常，系统将自动调用uncaughtException方法 <br/>
     * thread为未捕获的异常线程，ex为为捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 导出到sd卡
            dumpExceptionToSDCard(ex);
            // 上传异常信息至服务器
            uploadExceptionToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        ex.printStackTrace();
        // 如果系统提供了默认的异常处理器，则交给系统去结束层序，否则强制结束
        if (mDefaExceptionHandler != null) {
            LogUtils.i(TAG, "OS operation finish");
            mDefaExceptionHandler.uncaughtException(thread, ex);
            // android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        // 如果SD卡不存在或无法使用，则不能写入异常信息
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounte,skip dump exception");
                return;
            }
        }

        //
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            if (dir.getTotalSpace() > 1024 * 1024) {
                File[] f = dir.listFiles();
                for (int i = 0; i < f.length; i++) {
                    f[i].delete();
                }
            }
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date(current));
        mfile = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        if (!mfile.exists()) {
            mfile.createNewFile();
        }

        //
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(mfile)));
            pw.println(time);

            try {
                dumpPhoneInfo(pw);// 记录手机信息，保证兼容性
            } catch (NameNotFoundException e) {
                LogUtils.i(TAG, "导出手机信息失败");
            }
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (IOException e) {
            Log.e(TAG, "dump crash info failed");
        }

    }

    /**
     * 导出手机信息
     *
     * @throws NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

        // App版本信息
        pw.println("App Version: " + pi.versionName + "_" + pi.versionCode);

        // Android系统版本信息
        pw.println("OS Version: " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);

        // 手机制造商
        pw.println("Vendor: " + Build.MANUFACTURER);

        // 手机型号
        pw.println("Model: " + Build.MODEL);

        // CPU架构
        pw.println("CPU ABI: " + Build.CPU_ABI);
    }

    /**
     * 上传信息至服务器
     */
    public static void uploadExceptionToServer() {

    }

    public static boolean isExitPath(){
        // 如果SD卡不存在或无法使用，则不能写入异常信息
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounte,skip dump exception");
                return false;
            }
        }
        //
        File dir = new File(PATH);
        if (dir.exists()) {
                File[] f = dir.listFiles();
                if (f.length>0){
                    return true;
                }else{
                    return false;
                }

        }
        return false;
    }


}
