package com.cpcp.loto.config;


import com.cpcp.loto.BuildConfig;

/**
 * <p>
 * 功能描述：接口地址配置
 */

public class HostConfig {

    public static String getHost() {
        if (!BuildConfig.LOG_DEBUG) {
            //正式服务器地址
        }
        //测试服务器地址
        return "http://ad.076668.com/";
//        return "http://localhost:8080/";

    }

}
