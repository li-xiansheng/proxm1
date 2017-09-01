package com.muzikun.one.wxapi;//package com.muzikun.lhfsyczl.wxapi;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Window;
//
//import com.muzikun.lhfsyczl.R;
//import com.muzikun.lhfsyczl.activity.LoginActivity;
//import com.muzikun.lhfsyczl.activity.MainActivity;
//import com.muzikun.lhfsyczl.data.config.A;
//import com.muzikun.lhfsyczl.data.config.Config;
//import com.muzikun.lhfsyczl.data.model.UserModel;
//import com.muzikun.lhfsyczl.lib.util.Auth;
//import com.muzikun.lhfsyczl.lib.util.Helper;
//import com.muzikun.lhfsyczl.lib.util.Net;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.SendAuth;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by likun on 16/8/29.
// * URL    : www.muzikun.com
// * E-MAIL : 522525970@qq.com
// */
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    private final String TAG = this.getClass().getSimpleName();
//    public static final String APP_ID = Config.WX_APP_ID;
//    public static final String APP_SECRET = Config.WX_SECRET;
//    private IWXAPI mApi;
//    private String resultCode = null;
//    private Map<String,String> userInfo = null;
//    private UserModel userModel             = null;
//    private ProgressDialog progressDialog   = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        super.setContentView(R.layout.fragment_default);
//
//
//        if(MainActivity.mApi == null){
//            mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
//            mApi.registerApp(Config.WX_APP_ID);
//        }else{
//            mApi = MainActivity.mApi;
//        }
//
//        mApi.handleIntent(this.getIntent(), this);
//        Intent intent  = getIntent();
//        boolean flag = intent.getBooleanExtra("login",false);
//
//        if(flag) {
//            final SendAuth.Req req = new SendAuth.Req();
//            req.scope = "snsapi_userinfo";
//            req.state = this.getClass().getSimpleName();
//            mApi.sendReq(req);
//        }
//        finish();
//    }
//
//    //微信发送的请求将回调到onReq方法
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    //发送到微信请求的响应结果
//    @Override
//    public void onResp(BaseResp resp) {
//        progressDialog = ProgressDialog.show(WXEntryActivity.this,"认证成功，正在拉取用户资料",null);
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//
//                //发送成功
//                SendAuth.Resp resSendAuth =  (SendAuth.Resp)resp;
//                resultCode = resSendAuth.code;
//
//                new GetAcceccToken().execute(resultCode);
//
//                L("发送登陆2" );
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                //发送取消
//                L("发送登陆3");
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                //发送被拒绝
//                L("发送登陆4");
//                break;
//            default:
//                //发送返回
//                L("发送登陆5");
//                break;
//        }
//
//
//    }
//    public void L(String msg){
//        Log.i("LoginActivity",msg);
//    }
//
//    public class GetAcceccToken extends AsyncTask<String,String,String>{
//        /*
//        *
//        * {"access_token":"QIJnVuKWpgwB_QDu2sgqVxPxTJU8Ofvtya5ioLCzohim3DfnN7T2o7zg13fK8VqclQx001D74pfmywPb28ruoaXwWk5xMtP0g8EVCImPPEc","expires_in":7200,"refresh_token":"dTY9_51XXVyR_Y6IyRItXpBtyzlUCutNbVYH9_hza64k2CRUT536JZUAAUYXAfmt8nx5Fe-6SeOTEOcxF4OuDI_QmWdD8ldngM5rd213Q2k","openid":"oDIh7vyuLEWeo2Umpbhd1W2j_3Js","scope":"snsapi_userinfo","unionid":"omxVzwUaFiOMhMU9hBxIyluVe9fQ"}
//        * */
//        @Override
//        protected String doInBackground(String... params) {
//            String api = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Config.WX_APP_ID +"&secret="+Config.WX_SECRET+"&code="+resultCode+"&grant_type=authorization_code";
//            String result = Net.getApiJson(api);
//            userInfo = new HashMap<>();
//            if(result != "error"){
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    userInfo.put("access_token",jsonObject.getString("access_token"));
//                    userInfo.put("refresh_token",jsonObject.getString("refresh_token"));
//                    userInfo.put("unionid",jsonObject.getString("unionid"));
//                    userInfo.put("openid",jsonObject.getString("openid"));
//                    String api2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+jsonObject.getString("access_token")+"&openid="+jsonObject.getString("openid");
//                    return Net.getApiJson(api2);
//                } catch (JSONException e) {
//                    return null;
//                }
//            }else{
//                return null;
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            if(s != null){
//                L(s);
//                new GetServiceUserInfo(s).execute();
//
//            }else{
//                L("错误");
//            }
//        }
//    }
//
//    public class GetServiceUserInfo extends AsyncTask<String,String,String>{
//
//        String jsonData  = null;
//        String error ;
//
//        public GetServiceUserInfo(String jsonData) {
//            this.jsonData = jsonData;
//        }
//
//        @Override
//        protected String doInBackground(String... str) {
//
//            JSONObject jsonObject;
//
//            try {
//                jsonObject = new JSONObject(jsonData);
//
//                String apiString = A.GET_INFO_BY_QQ;
//
//                String userSex = "保密";
//                if(jsonObject.getInt("sex")==1)
//                            userSex =  "男";
//                else if (jsonObject.getInt("sex")==2)
//                            userSex = "女";
//
//                String params =
//                        "uname=" + jsonObject.getString("nickname")
//                                + "&gender="     + userSex
//                                + "&openid="     + jsonObject.getString("openid")
//                                + "&type="      +    "weixin"
//                                + "&face="       + URLEncoder.encode(jsonObject.getString("headimgurl"));
//
//                String request ;
//
//                try {
//                    byte[] requestByte = Net.sendPostRequestByForm(apiString,params);
//                    request = new String(requestByte,"UTF-8");
//                } catch (Exception e) {
//                    error = "网络连接失败，请检查网络是否通畅";
//                    Log.i("QQ_ERROR",error);
//                    request = null;
//                }
//                return request;
//            } catch (JSONException e) {
//                error = "网络连接失败，请检查网络是否通畅-1";
//                Log.i("QQ_ERROR",error);
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.i("QQ_REQUEST2",s.toString());
//            userModel =  new UserModel(s.toString());
//            Map<String, String> userInfo = userModel.getUser();
//
//            if(userInfo==null){
//
//                Helper.T(WXEntryActivity.this,"获取用户信息失败,请稍后再试.");
//                progressDialog.dismiss();
//
//            }else{
//
//                UserModel.saveUser(WXEntryActivity.this,userInfo);
//                Auth.setLogin(WXEntryActivity.this,true);
//                Intent intent = new Intent();
//                intent.putExtra("USER_INFO",s.toString());
//                setResult(RESULT_FIRST_USER,intent);
//                Helper.T(WXEntryActivity.this,"登录成功");
////                progressDialog.dismiss();
//                WXEntryActivity.this.finish();
//               if(LoginActivity.selfActivity!=null) {
//                   LoginActivity.selfActivity.finish();
//               }
//            }
//        }
//    }
//}
