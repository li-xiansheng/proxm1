package com.cpcp.loto.net;


import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.BaseResponseEntity;
import com.cpcp.loto.entity.BaseResponseListEntity;
import com.cpcp.loto.entity.ImgResponseEntity;
import com.cpcp.loto.entity.LotoKingEntity;
import com.cpcp.loto.entity.RedPacketEntity;
import com.cpcp.loto.entity.TrendAnalysisEntity;
import com.cpcp.loto.entity.UserInfoEntity;
import com.cpcp.loto.entity.WinningEntity;

import java.util.List;
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
     * index.php?g=user&m=register&a=doregister
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
     * 领取红包
     *
     * @param params 上传参数组
     * @return 转成一个Rxjava被观察者
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=get_redpacket")
    Observable<BaseResponse2Entity<RedPacketEntity>> getRedPacket(@FieldMap Map<String, String> params);

    /**
     * 玄机锦囊
     *
     * @return 转成一个Rxjava被观察者
     */
    @POST("index.php?g=portal&m=article&a=xunjijinnang")
    Observable<BaseResponse2Entity<String>> getXuanJiPacket();

    /**
     * 获取高手资料-周榜-月榜信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=liuhewang")
    Observable<BaseResponse2Entity<List<LotoKingEntity>>> getLotoKing(@FieldMap Map<String, String> map);

    /**
     * 获取高手资料-连胜榜
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=lianshengbang")
    Observable<BaseResponse2Entity<List<WinningEntity>>> getWinning(@FieldMap Map<String, String> map);

    /**
     * 发布心水
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=add_xinshui")
    Observable<BaseResponse2Entity<Object>> sendXinShui(@FieldMap Map<String, String> map);

    /**
     * 走势分析
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=portal&m=article&a=zoushifenxi")
    Observable<BaseResponse2Entity<List<TrendAnalysisEntity>>> getTrend(@FieldMap Map<String, String> map);




    /**
     * 开奖直播地址
     */
    public static String lotteryLive = "http://lac.83gw.com/index.php?g=portal&m=Index&a=sskj";
    /**
     * 六合图库
     */
    public static String lotoPictures = "http://lac.83gw.com/index.php?g=portal&m=Index&a=lhtk";
    /**
     * 历史开奖
     */
    public static String historyLottery = "http://lac.83gw.com/index.php?g=portal&m=Index&a=kaijianglishi";

    /**
     * 查询助手
     */
    public static String queryHelper = "http://lac.83gw.com/index.php?g=portal&m=Index&a=chaxunzhushou";

    /**
     * 六合资料
     */
    public static String lotoInfo = "http://lac.83gw.com/index.php?g=portal&m=Index&a=liuheziliao1";
    /**
     * 六合统计
     */
    public static String lotoStatistics = "http://lac.83gw.com/index.php?g=portal&m=article&a=liuhetongji&type=50";

    /**
     * 属性参照
     */
    public static String attributeReference = "http://lac.83gw.com/index.php?g=portal&m=article&a=shuxingcanzhao";
    /**
     * 特码历史
     */
    public static String temaHistory = "http://lac.83gw.com/index.php?g=portal&m=article&a=temalishi&type=50";

    /**
     * 正码历史
     */
    public static String orthocodeHistory = "http://lac.83gw.com/index.php?g=portal&m=article&a=zhengmalishi&type=50";

    /**
     * 尾数大小
     */
    public static String tailSize = "http://lac.83gw.com/index.php?g=portal&m=article&a=weishudaxiao&type=2017";

    /**
     * 生肖特码
     */
    public static String animalTema = "http://lac.83gw.com/index.php?g=portal&m=article&a=shengxiaotemalengretu&type=50";

    /**
     * 生肖正码
     */
    public static String animalOrthocode = "http://lac.83gw.com/index.php?g=portal&m=article&a=shengxiaozhengmalengretu&type=50";
    /**
     * 波色特码
     */
    public static String boseTema = "http://lac.83gw.com/index.php?g=portal&m=article&a=bosetema&type=50";
    /**
     * 波色正码
     */
    public static String boseOrthocode = "http://lac.83gw.com/index.php?g=portal&m=article&a=bosezhengma&type=50";
    /**
     * 特码两面
     */
    public static String TemaBothSides = "http://lac.83gw.com/index.php?g=portal&m=article&a=temaliangmian&type=50";

    /**
     * 特码尾数
     */
    public static String TemaMantissa = "http://lac.83gw.com/index.php?g=portal&m=article&a=temaweishu&type=50";

    /**
     * 正码尾数
     */
    public static String OrthocodeMantissa = "http://lac.83gw.com/index.php?g=portal&m=article&a=zhengmaweishu&type=50";

    /**
     * 正码总分
     */
    public static String OrthocodeTotal = "http://lac.83gw.com/index.php?g=portal&m=article&a=zhengmazongfen&type=50";
    /**
     * 号码波段
     */
    public static String numberBand = "http://lac.83gw.com/index.php?g=portal&m=article&a=haomaboduan&type=50";

    /**
     * 家禽野兽
     */
    public static String animal = "http://lac.83gw.com/index.php?g=portal&m=article&a=jiaqinyeshou&type=2017";

    /**
     * 连码走势
     */
    public static String jointMark = "http://lac.83gw.com/index.php?g=portal&m=article&a=lianmazoushi&type=2017";
    /**
     * 连肖走势
     */
    public static String lianxiao = "http://lac.83gw.com/index.php?g=portal&m=article&a=lianxiaozoushi&type=2017";



}
