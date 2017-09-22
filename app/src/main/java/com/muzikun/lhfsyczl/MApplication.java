package com.muzikun.lhfsyczl;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.muzikun.lhfsyczl.util.AppUtils;
import com.muzikun.one.base.App;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * <p>
 * 功能描述：继承系统入口，添加配置属性,继承于分包处理Application--
 * 因为要保持依赖项目-appOne中的是否登录-置于全局Application中，故此处继承了依赖工程的Application
 */

public class MApplication extends App {

    private final String TAG = this.getClass().getSimpleName();
    public static MApplication instance;
    public static Context applicationContext;

    public static Context getInstance() {
        return instance;
    }


    //六合风水需要
    public boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        //初始化内存检测，debug时有效，release时无效
//        LeakCanary.install(this);
        // Normal app init code...
        applicationContext = this;
        instance = this;

        //bugly初始化
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppPackageName(AppUtils.getAppName());
        strategy.setAppVersion(AppUtils.getVersionCode() + "");

        //Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "f151eb182c", false);

        //数据库初始化
//        LitePal.initialize(this);
        //
        // 捕获未知异常-本地sd自己用
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
        //
        //第一：默认初始化
        Bmob.initialize(this, "054adec3433e649ecad9deefd0972de8");
        //极光
        boolean debugMode=BuildConfig.LOG_DEBUG?true:false;
        JPushInterface.setDebugMode(debugMode);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        initLiuHeFengShui();
    }

    /**
     * 六合风水需要
     */
    private void initLiuHeFengShui() {
        //okHttpUtils初始化
//		ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
//				.cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    //重写multdex ，64k限制需要
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
