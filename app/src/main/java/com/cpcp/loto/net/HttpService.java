package com.cpcp.loto.net;


import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.ImgResponseEntity;
import com.cpcp.loto.entity.UserInfoEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    /**
     * 获取我的关注列表
     * index.php?g=portal&m=article&a=del_friend
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=del_friend")
    Observable<BaseResponse2Entity<String>> cancelAttention(@FieldMap Map<String, String> map);

    /**
     * 取消关注
     * index.php?g=portal&m=article&a=myfriends
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=myfriends")
    Observable<BaseResponse2Entity<String>> getMyAttention(@FieldMap Map<String, String> map);

    /**
     * 获取我的购买记录
     * index.php?g=portal&m=article&a=buy_log
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=buy_log")
    Observable<BaseResponse2Entity<String>> getBuyRecord(@FieldMap Map<String, String> map);

    /**
     * 获取我的转换记录
     * index.php?g=portal&m=article&a=duihuan_log
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=duihuan_log")
    Observable<BaseResponse2Entity<String>> getChangeRecord(@FieldMap Map<String, String> map);

    /**
     * 获取本期推荐
     * index.php?g=portal&m=article&a=current_recommendation
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=current_recommendation")
    Observable<BaseResponse2Entity<String>> getCurrentRecommend(@FieldMap Map<String, String> map);

    /**
     * 查看本期推荐
     * index.php?g=portal&m=article&a=see_current_recommendation
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=see_current_recommendation")
    Observable<BaseResponse2Entity<String>> seeCurrentRecommend(@FieldMap Map<String, String> map);

    /**
     * 获取历史推荐
     * index.php?g=portal&m=article&a=historical_recommendation
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=historical_recommendation")
    Observable<BaseResponse2Entity<String>> getHistoryRecommend(@FieldMap Map<String, String> map);
//    index.php?g=portal&m=article&a=historical_recommendation


    /**
     * 查看历史推荐
     * index.php?g=portal&m=article&a=see_historical_recommendation
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=see_historical_recommendation")
    Observable<BaseResponse2Entity<String>> seeHistoryRecommend(@FieldMap Map<String, String> map);

    /**
     * 我的财神
     * index.php?g=portal&m=article&a=mymammon
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=mymammon")
    Observable<BaseResponse2Entity<String>> getMyMammom(@FieldMap Map<String, String> map);

    /**
     * 发布心水
     * index.php?g=portal&m=article&a=add_xinshui
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=add_xinshui")
    Observable<BaseResponse2Entity<String>> publicXinShui(@FieldMap Map<String, String> map);

    /**
     * 我的粉丝
     * index.php?g=portal&m=article&a=myfensi
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=myfensi")
    Observable<BaseResponse2Entity<String>> getMyFensi(@FieldMap Map<String, String> map);

    /**
     * 提交转换
     * index.php?g=portal&m=article&a=add_duihuan
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=add_duihuan")
    Observable<BaseResponse2Entity<String>> submitChange(@FieldMap Map<String, String> map);

    /**
     * 获取转换平台名称等信息
     * index.php?g=portal&m=article&a=get_pingtai
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=get_pingtai")
    Observable<BaseResponse2Entity<String>> getPingTaiInfo(@FieldMap Map<String, String> map);

}
