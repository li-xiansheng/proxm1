package com.cpcp.loto.net;


import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.BaseResponseEntity;
import com.cpcp.loto.entity.BaseResponseListEntity;
import com.cpcp.loto.entity.ImgResponseEntity;
import com.cpcp.loto.entity.UserInfoEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 功能描述：Retrofit接口类，声明所有和后台对接数据的接口
 */

public interface HttpService {

    /**
     * 登录接口
     * index.php?g=user&m=login&a=dologin
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=user&m=login&a=dologin")
    Observable<BaseResponse1Entity<Object>> login(@FieldMap Map<String, String> map);



    /**
     * 注册
     *index.php?g=user&m=register&a=doregister
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=user&m=register&a=doregister")
    Observable<BaseResponse1Entity<String>> register(@FieldMap Map<String, String> map);

    /**
     * 发送短信验证码
     * index.php?g=user&m=register&a=send_sms
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=user&m=register&a=send_sms")
    Observable<BaseResponse1Entity<String>> sendSMS(@FieldMap Map<String, String> map);


    /**
     * 获取用户基本信息
     * index.php?m=article&a=getuserinfo
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=article&a=getuserinfo")
    Observable<BaseResponse2Entity<UserInfoEntity>> getUserInfo(@FieldMap Map<String, String> map);


    /**
     * 上传头像
     *
     * @param params 上传参数组
     * @return 转成一个Rxjava被观察者
     */
    @Multipart
    @POST("index.php?g=user&m=profile&a=avatar_upload")
    Observable<BaseResponse2Entity<ImgResponseEntity>> uploadPicToMine(@PartMap Map<String, RequestBody> params);

    /**
     * 开奖直播地址
     */
    public static String lotteryLive="http://lac.83gw.com/index.php?g=portal&m=Index&a=sskj";
    /**
     * 六合图库
     */
    public static String lotoPictures="http://lac.83gw.com/index.php?g=portal&m=Index&a=lhtk";
    /**
     * 历史开奖
     */
    public static String historyLottery="http://lac.83gw.com/index.php?g=portal&m=Index&a=kaijianglishi";

    /**
     * 查询助手
     */
    public static String queryHelper="http://lac.83gw.com/index.php?g=portal&m=Index&a=chaxunzhushou";

    /**
     * 六合资料
     */
    public static String lotoInfo="http://lac.83gw.com/index.php?g=portal&m=Index&a=liuheziliao1";
    /**
     * 六合统计
     */
    public static String lotoStatistics="http://lac.83gw.com/index.php?g=portal&m=article&a=liuhetongji&type=50";

}
