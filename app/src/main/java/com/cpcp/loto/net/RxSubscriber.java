package com.cpcp.loto.net;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.net.ParseException;

import com.alibaba.fastjson.JSON;
import com.cpcp.loto.MApplication;
import com.cpcp.loto.config.ApiConstants;
import com.cpcp.loto.exception.ApiException;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.NetworkUtil;
import com.cpcp.loto.util.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * 功能描述：封装Subscriber,封装网络请求订阅者，提供给RxJava+Retrofit封装
 * 参考博文：http://blog.csdn.net/jdsjlzx/article/details/51882661
 * http://blog.csdn.net/u012551350/article/details/51445357
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private final String TAG = this.getClass().getSimpleName();
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    //是否显示错误
    private boolean isShowMeg = true;
    /**
     * 访问网络的当前Activity
     */
    protected Activity currentActivity;

    @Override
    public void onStart() {
        super.onStart();
        currentActivity = this.getCurrentActivity();
        //显示Loading
        if (currentActivity != null) {
            LoadingDialog.showDialog(currentActivity);
        }
    }

    @Override
    public void onCompleted() {
        // 关闭Loading
        if (currentActivity != null) {
            LoadingDialog.closeDialog(currentActivity);
        }
    }

    @Override
    public void onError(Throwable e) {
        //关闭Loading，显示错误信息
        if (currentActivity != null) {
            LoadingDialog.closeDialog(currentActivity);
        }
        ApiException ex;
        if (!NetworkUtil.isConnected(MApplication.getInstance())) {
            ex = new ApiException(e, ApiConstants.NETWORD_ERROR);
            ex.msg = "网络不可用!请检查网络设置";
            onErrorBase(ex);
            return;
        } else if (e instanceof NetworkErrorException) {
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            ex.msg = "网络错误!请检查网络是否正常";  //均视为网络错误
            onErrorBase(ex);
            return;
        } else if (e instanceof ConnectException) {
            ConnectException connectException = (ConnectException) e;
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            ex.msg = "服务器累了，正在休息中……\n请稍后尝试!";
            LogUtils.i(TAG, connectException.getCause() + "");
            onErrorBase(ex);
            return;
        } else if (e instanceof SocketTimeoutException) {//请求超时
            SocketTimeoutException connectException = (SocketTimeoutException) e;
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            ex.msg = "请求超时……\n请稍后尝试!";
            LogUtils.i(TAG, connectException.getMessage() + "");
            onErrorBase(ex);
            return;
        } else if (e instanceof SSLHandshakeException) {
            SSLHandshakeException sslHandshakeException = (SSLHandshakeException) e;
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            ex.msg = "访问证书被拒绝，请与我们联系!";
            LogUtils.i(TAG, sslHandshakeException.getMessage() + "");
            onErrorBase(ex);
            return;
        } else if (e instanceof UnknownHostException) {
            UnknownHostException unknownHostException = (UnknownHostException) e;
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            ex.msg = "网络连接失败，不能解析主机";
            LogUtils.i(TAG, unknownHostException.getMessage() + "");
            onErrorBase(ex);
            return;
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ApiConstants.HTTP_ERROR);
            int code = httpException.code();
            switch (code) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.msg = "网络错误，连接服务器失败";  //均视为网络错误
                    break;
            }
            onErrorBase(ex);
        } else if (e instanceof NoRouteToHostException) {
            ex = new ApiException(e, ApiConstants.NETWORD_ERROR);
            ex.msg = "服务暂停了，请等候服务恢复";            //
            onErrorBase(ex);
        } else if (e instanceof com.alibaba.fastjson.JSONException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ApiConstants.PARSE_ERROR);
            ex.msg = "数据解析错误，请与我们联系";            //均视为解析错误
            e.getStackTrace();
            e.printStackTrace();
            onErrorBase(ex);

        } else {
            ex = new ApiException(e, ApiConstants.UNKNOWN);
            ex.msg = "请求失败，未知错误";          //未知错误
//            onErrorBase(ex);
        }
    }

    @Override
    public void onNext(T t) {
        LogUtils.i(TAG, "请求返回原始数据" + JSON.toJSONString(t));
        if (t == null) {
            ToastUtils.show("没有数据了");
            return;
        }
        if (t instanceof String) {
            _onNext(0, t);
        } else if (t instanceof List) {
            _onNext(0, t);
        } else {
            _onNext(0, t);
        }
//        else {
//            BaseResponseEntity<T> json = (BaseResponseEntity<T>) t;//此处解析后，实现类依然需要解析一次//TODO
//            int status = json.getStatus();
//            _onNext(status, t);
//        }

    }

    /**
     * 显示自定义异常
     *
     * @param ex 自定义接口异常信息
     */
    private void onErrorBase(ApiException ex) {
        int code = ex.code;
        String msg = ex.msg;
        LogUtils.i(TAG, "元数据错误：code:" + code + "\nmsg" + msg);

        _onError(code, msg);
        if (isShowMeg) {
            ToastUtils.show(msg);
        }
    }

    /**
     * 抽象方法， 没次请求必须让请求的Activity返回当前Activity
     *
     * @return 当前Activity，用于创建Dialog,返回null则不显示dialog
     */
    public abstract Activity getCurrentActivity();

    /**
     * 请求有数据返回时必须实现的虚方法，
     *
     * @param status   消息状态码
     * @param response 原始数据
     */
    public abstract void _onNext(int status, T response);

    /**
     * 覆写该方法处理错误逻辑
     *
     * @param status 消息状态码
     * @param msg    错误信息
     */
    public void _onError(int status, String msg) {
    }

}
