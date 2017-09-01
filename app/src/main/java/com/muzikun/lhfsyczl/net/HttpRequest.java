package com.muzikun.lhfsyczl.net;


import android.content.Context;

import com.muzikun.lhfsyczl.MApplication;
import com.muzikun.lhfsyczl.config.HostConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import com.muzikun.lhfsyczl.BuildConfig;
import com.muzikun.lhfsyczl.util.SPUtil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 功能描述：网络请求框架封装Retrofit+okHttp
 */

public class HttpRequest {

    private volatile static HttpRequest instance;

    /**
     * 该类单例模式，获取类实例
     *
     * @return 该类示例对象
     */
    public static HttpRequest getInstance() {
        if (null == instance) {
            synchronized (HttpRequest.class) {
                if (null == instance) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }

    private HttpRequest() {
    }

    /**
     * 产生转化为Json对象的解析
     *
     * @return 封装后的Retrofit, 每次请求时调用HttpsRequest.provideClientApi()即可获取Retrofit实例
     */
    public static HttpService provideClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostConfig.getHost())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpRequest.getInstance().getOkHttpClient())
                .build();
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService;
    }

    /**
     * 产生转化为String的解析
     *
     * @return 封装后的Retrofit, 每次请求时调用HttpsRequest.provideClientApi()即可获取Retrofit实例
     */
    public static HttpService provideClientApiToString() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HostConfig.getHost())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpRequest.getInstance().getOkHttpClient())
                .build();
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService;
    }

    /**
     * okHttpClient通用设置为https访问-使用拦截器设置请求头，设置https，设置日志拦截分析……
     *
     * @return OkHttpClient的builder对象，构建完整的Http请求设置
     */
    public OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//        try {
//            //设置https
//            SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
//            if (sslSocketFactory != null)
//                okHttpClient.sslSocketFactory(sslSocketFactory);
//        } catch (Exception e) {
//            ToastUtils.show("你的手机系统版本较低，暂不支持该软件使用");
//            e.printStackTrace();
//        }
        //设置超时
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS);//
        okHttpClient.readTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        okHttpClient.retryOnConnectionFailure(true);


        //设置是否打印Log//打印请求log日志//根据是否debug模式决定
        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(loggingInterceptor);
        }

        okHttpClient.addInterceptor(new ReceivedCookiesInterceptor(MApplication.getInstance()));


        SPUtil sp = new SPUtil(MApplication.getInstance(), "cookie");
        final String cookie = sp.getString("cookie", "");

        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")//设定上传形式和编码格式
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", cookie)
                        .build();
                return chain.proceed(request);
            }

        });
        okHttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return okHttpClient.build();
    }

    /**
     * 获取cookie
     */
    public class ReceivedCookiesInterceptor implements Interceptor {
        private Context context;

        public ReceivedCookiesInterceptor(Context context) {
            super();
            this.context = context;

        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            //这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();
                //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
                Observable.from(originalResponse.headers("Set-Cookie"))
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                String[] cookieArray = s.split(";");
                                return cookieArray[0];
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String cookie) {
                                cookieBuffer.append(cookie).append(";");
                            }
                        });
                SPUtil sp = new SPUtil(MApplication.getInstance(), "cookie");
                sp.putString("cookie", cookieBuffer.toString());

            }

            return originalResponse;
        }

    }
}
