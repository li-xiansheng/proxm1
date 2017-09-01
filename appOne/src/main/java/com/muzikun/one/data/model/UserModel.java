package com.muzikun.one.data.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by likun on 16/6/23.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class UserModel {

    private String jsonString                            = null;

    private Map<String,String> userMap                   = null;

    public  static Map<String,String> key                = new HashMap<>();

    public static String type                            = null;

    private static final String USER_AUTH                = "USER_AUTH";
    public  static final String USER_ID                  = "USER_ID";
    public  static final String USER_PHONE               = "USER_PHONE";
    public  static final String USER_PASS                = "USER_PASS";
    public  static final String USER_NAME                = "USER_NAME";
    public  static final String USER_ICON                = "USER_ICON";

    public static final String MODEL_DEFAULT             = "DEFAULT";
    public static final String MODEL_QQ                  = "QQ";

    private static SharedPreferences sharedPreferences   = null;


    public UserModel(String jsonString) {
        this.jsonString = jsonString;
        if(phoneValidate(jsonString)){
            type = this.MODEL_DEFAULT;
        }else{
            type = this.MODEL_QQ;
        }
    }

    /**
     * 对登录结果进行验证
     * @param str
     * @return
     */
    public boolean phoneValidate(String str){
        try {
            JSONObject jsonObject = new JSONObject(str);
            int code = jsonObject.getInt("code");
            if(code>=0){
                return true;
            }else{
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }
    /**
     * 初始化键值对
     */
    static{
        key.put(USER_ID     ,"mid");
        key.put(USER_PHONE  ,"userid");
        key.put(USER_PASS   ,"pwd");
        key.put(USER_NAME   ,"uname");
        key.put(USER_ICON   ,"face");
    }

    /**
     * 保存用户信息
     * @param context
     * @param userInfo
     */
    public static void saveUser(Context context ,Map<String,String> userInfo){
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        for (String key: userInfo.keySet()) {
            edit.putString(key,userInfo.get(key).toString());
        }
        edit.commit();
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static Map<String, String> getUser(Context context){
        Map<String, String> userInfo = new HashMap<>();
        for (String keySet: key.keySet()) {
            userInfo.put(keySet,getSharedPreferences(context).getString(keySet,null));
        }
        return userInfo;
    }

    /**
     * 清除用户数据
     * @param context
     */
    public static void removeUser(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        for (String keySet:key.keySet()) {
            editor.remove(keySet);
        }
        editor.commit();
        sharedPreferences = null;
    }

    /**
     * 获取用户信息
     * @return
     */
    public Map<String, String> getUser() {
        userMap = jsonToMap(jsonString,type);
        return userMap;
    }


    /**
     * 获取 存储驱动
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferences    ==null){
            sharedPreferences   = context.getSharedPreferences(USER_AUTH,context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 将JSON转化成Map格式的数据
     * @param json
     * @return
     */
    private Map<String, String> jsonToMap(String json){
        Map<String,String> myUserMap    = new HashMap<>();
        try {
            JSONObject userJsonObject   = new JSONObject(json).getJSONObject("data");
            for (String k:key.keySet()) {
                myUserMap.put(k,userJsonObject.getString(key.get(k)));
            }
            return myUserMap;
        } catch (JSONException e) {
            return null;
        }
    }
    /**
     * 将JSON转化成Map格式的数据
     * @param json
     * @return
     */
    private Map<String, String> jsonToMap(String json,String type){
        switch(type){
            case UserModel.MODEL_DEFAULT:
                return  jsonToMap(json);
            case UserModel.MODEL_QQ:
                return  qqJsonToMap(json);
            default:
                return null;
        }
    }

    private Map<String, String> qqJsonToMap(String json){
        Map<String,String> myUserMap    = new HashMap<>();
        try {
            JSONObject userJsonObject   = new JSONObject(json);
            myUserMap.put(USER_NAME,userJsonObject.getString("nickname"));
            myUserMap.put(USER_ICON,userJsonObject.getString("figureurl_qq_2"));
            myUserMap.put(USER_PHONE,userJsonObject.getString("nickname"));
            myUserMap.put(USER_PASS,userJsonObject.getString("figureurl_qq_2"));
            getQQMid(userJsonObject);
            return myUserMap;
        } catch (JSONException e) {
            return null;
        }
    }

    private void getQQMid(JSONObject userJsonObject){

    }

}
