package com.muzikun.one.lib.util;//package com.muzikun.lhfsyczl.lib.util;
//
//import android.content.Context;
//
//import com.muzikun.lhfsyczl.data.config.Config;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
///**
// * Created by likun on 16/8/28.
// * URL    : www.muzikun.com
// * E-MAIL : 522525970@qq.com
// */
//public class WX {
//    private static IWXAPI API = null;
//    public static IWXAPI getApi(Context context){
//        if(API == null ){
//            API = WXAPIFactory.createWXAPI(context , Config.WX_APP_ID,true);
//            API.registerApp(Config.WX_APP_ID);
//        }
//        return API;
//    }
//}
