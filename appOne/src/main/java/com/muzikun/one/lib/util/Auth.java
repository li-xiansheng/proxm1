package com.muzikun.one.lib.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.muzikun.one.activity.LoginActivity;
import com.muzikun.one.base.App;
import com.muzikun.one.data.config.Config;
import com.muzikun.one.data.model.UserModel;

import java.util.Map;

/**
 * Created by likun on 16/6/23.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class Auth {
    private Activity context                                = null;
    private String userPhone                                = null;
    private String userPass                                 = null;
    private Map<String,String> userInfo                     = null;
    private OnAuthCallbackListener onAuthCallbackListener   = null;
    private static App app                                  = null;


    private Auth(){}

    private static class LazyHolder{
        private static final Auth AUTH  = new Auth();
    }

    public static final Auth getInstance(){
        return LazyHolder.AUTH;
    }

    /**
     *登录函数,如果已网络登录,则自动加载数据,如果没有登录则跳转登录页面
     * @param activity
     * @return
     */
    public Map<String,String> login( Activity activity){
        this.context =activity;

        this.app    = (App) context.getApplication();

        userInfo    = UserModel.getUser(context);

        userPhone   = userInfo.get(UserModel.USER_PHONE);

        userPass    = userInfo.get(UserModel.USER_PASS);


        //!app.isLogin
        if(stringCheck(userPhone,userPass)){
            setLogin(context,true);
            return UserModel.getUser(context);
        }else {
            return null;
        }
    }

    /**
     * 退出登录
     * @param activity
     */
    public static void logout( Activity activity){
        app.isLogin = false;
        UserModel.removeUser(activity);
    }

    /**
     * 启动登录界面
     * @param activity
     */
    public void startLogin(Activity activity ,OnAuthCallbackListener callbackListener){
        setOnAuthCallbackListener(callbackListener);
        Intent it       = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(it, activity.RESULT_CANCELED);
    }

    /**
     * 判断是否登录
     * @param context
     * @return
     */
    public static boolean isLogin( Activity context){
        app = (App) context.getApplication();
        return app.isLogin;
    }

    /**
     * 设置登录状态
     * @param context
     * @param isLogin
     * @return
     */
    public static boolean setLogin( Activity context ,boolean isLogin){
        app = (App) context.getApplication();
        app.isLogin =isLogin;
        return app.isLogin;
    }

    /**
     * 验证登录状态
     * @param phone
     * @param pass
     * @return
     */
    private boolean stringCheck(String phone , String pass) {
        if(phone == null || pass == null)
            return false;
        return true;
    }

    public String getAccessToken(){
        if(isLogin(context)){

            try {
                return Encrypt.MD5(userInfo.get(UserModel.USER_PHONE).toString() + Config.SECRET + userInfo.get(UserModel.USER_PASS).toString());
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 事件处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onFragmentResult(int requestCode, int resultCode, Intent data){
        if(onAuthCallbackListener == null) return;
        try {
            switch (resultCode) {
                case 0:
                    onAuthCallbackListener.onCancel(data);
                    break;
                case -1:
                    onAuthCallbackListener.onError(data);
                    break;
                case 1:
                    app.isLogin = true;
                    onAuthCallbackListener.onSuccess(data);
                    break;
                default:
                    Log.i("Auth","onActivityResult:default");
            }
        }catch (Exception e){

        }
    }

    /**
     * 设置监听事件
     * @param onAuthCallbackListener
     */
    public void setOnAuthCallbackListener(OnAuthCallbackListener onAuthCallbackListener) {
        this.onAuthCallbackListener = onAuthCallbackListener;
    }


    public static void onActivityResult(FragmentManager fragmentManager,int requestCode, int resultCode, Intent data){
        for(int i =0 ; i < fragmentManager.getFragments().size() ; i ++){
            try {
                fragmentManager.getFragments().get(i).onActivityResult(requestCode, resultCode, data);
            }catch (Exception e){

            }
        }
    }


    /**
     * 事件接口
     */
    public interface OnAuthCallbackListener {
         void onSuccess(Intent data);
         void onError(Intent data);
         void onCancel(Intent data);
    }

}

