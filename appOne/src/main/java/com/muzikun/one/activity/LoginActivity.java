package com.muzikun.one.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muzikun.one.R;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by likun on 16/6/22.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private LinearLayout goBackButton = null;
    private Button loginButton = null;
    private EditText userPhone = null;
    private EditText userPass = null;
    private String error = null;
    public static LoginActivity selfActivity = null;
    private UserModel userModel = null;
    private TextView forgetPassText = null;
    private TextView registerText = null;
    //    private Tencent mTencent                = null;
//    private MyQQLoginListener myLis = null;
    //    private ImageView qqLogo                = null;
//    private ImageView wxLogo                = null;
    private ProgressDialog progressDialog = null;

//    private JSONObject resphone = null;

    private String openId = null;
//    private IWXAPI wxApi                   = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_one);
        Helper.setColor(this, Color.parseColor("#891C21"));
        selfActivity = this;
        initView();
    }

    /**
     * 加载控件
     */
    private void initView() {
        goBackButton = (LinearLayout) findViewById(R.id.activity_login_back);
        loginButton = (Button) findViewById(R.id.activity_login_loginbutton);
        userPhone = (EditText) findViewById(R.id.activity_login_userphone);
        userPass = (EditText) findViewById(R.id.activity_login_userpass);
        forgetPassText = (TextView) findViewById(R.id.activity_login_forget_pass);
        registerText = (TextView) findViewById(R.id.activity_login_register);
//        qqLogo          = (ImageView) findViewById(     R.id.activity_login_qqlogo);
//        wxLogo          = (ImageView) findViewById(     R.id.activity_login_weixinlogo);


        goBackButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgetPassText.setOnClickListener(this);
        registerText.setOnClickListener(this);
//        qqLogo          .setOnClickListener(this);
//        wxLogo          .setOnClickListener(this);
    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.activity_login_back) {
            finish();

        } else if (i == R.id.activity_login_loginbutton) {
            login();

        } else if (i == R.id.activity_login_forget_pass) {
            register();

        } else if (i == R.id.activity_login_register) {
            register();


//            case R.id.activity_login_qqlogo:
//                qqLogo();
//                break;
//            case R.id.activity_login_weixinlogo:
//                wxLogin();
//                break;
        } else {
        }
    }

    private void register() {
        Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(it);
    }

//    private void qqLogo() {
//        mTencent = Tencent.createInstance(Config.QQ_APP_ID, getApplicationContext());
//        if (!mTencent.isSessionValid()) {
//            myLis = new MyQQLoginListener();
//            mTencent.login(LoginActivity.this, "all", myLis);
//        }
//    }
//
//
//    private void wxLogin() {
//        Intent intent = new Intent(LoginActivity.this, WXEntryActivity.class);
//        intent.putExtra("login", true);
//        startActivity(intent);
//
//
//    /*
//        wxApi = WX.getApi(this);
//
//        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = this.getClass().getSimpleName();
//        wxApi.sendReq(req);
//        wxApi.handleIntent(getIntent(),this);
//        T("正在开启微信登录...");
//    */
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
//        wxApi.handleIntent(intent, this);
    }

//   @Override
//    public void onReq(BaseReq baseReq) {
//        L("返回1");
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        L("返回2");
//    }


    /**
     * 登录操作
     */
    private void login() {
        String phone = userPhone.getText().toString();
        String pass = userPass.getText().toString();
        if (stringCheck(phone, pass)) {
            new ValidateUserInfo().execute(phone, pass);
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 输入验证
     *
     * @param phone
     * @param pass
     * @return
     */
    private boolean stringCheck(String phone, String pass) {

        if (phone.length() != 11) {
            error = "手机号码长度必须为11位数字";
            return false;
        }

        if (pass == "" || pass == null || pass.equals(null) || pass.length() < 6 || pass.length() > 18) {
            error = "密码长度必须在6-18位";
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        Tencent.onActivityResultData(requestCode, resultCode, data, myLis);
//        L("返回e");
//        if (requestCode == Constants.REQUEST_API) {
//            if (resultCode == Constants.REQUEST_QQ_SHARE ||
//                    resultCode == Constants.REQUEST_QZONE_SHARE ||
//                    resultCode == Constants.REQUEST_OLD_SHARE) {
//                Tencent.handleResultData(data, myLis);
//            }
//        }
    }

    /**
     * 进行服务器验证
     */
    public class ValidateUserInfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            byte[] resultByte;
            String resultString;
            try {
                resultByte = Net.sendPostRequestByForm(A.getUserLogin(), "userphone=" + strings[0] + "&userpass=" + strings[1]);
                resultString = new String(resultByte, "UTF-8");
            } catch (Exception e) {
                resultString = null;
            }
            return resultString;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Toast.makeText(LoginActivity.this, "请求失败,请检查网络链接是否畅通", Toast.LENGTH_SHORT).show();
            } else {

                if (validate(s)) {
                    userModel = new UserModel(s);
                    Map<String, String> userInfo = userModel.getUser();
                    if (userInfo == null) {
                        Helper.T(LoginActivity.this, "获取用户信息失败,请稍后再试.");
                    } else {
                        UserModel.saveUser(LoginActivity.this, userInfo);
                        Auth.setLogin(LoginActivity.this, true);
                        //Helper.T(LoginActivity.this,"保存成功!" + userInfo.get(UserModel.USER_PHONE) + " -- "+ userInfo.get(UserModel.USER_PASS));
                        Intent intent = new Intent();
                        intent.putExtra("USER_INFO", s);
                        setResult(RESULT_FIRST_USER, intent);
                        finish();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误,请检查输入是否有误", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * 对登录结果进行验证
     *
     * @param str
     * @return
     */
    public boolean validate(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }

//    public class MyQQLoginListener implements IUiListener {
//
//        @Override
//        public void onComplete(Object response) {
//            progressDialog = ProgressDialog.show(LoginActivity.this, "认证成功，正在拉取用户资料", null);
//            try {
//                resphone = new JSONObject(response.toString());
//                openId = resphone.getString("openid");
//                String params = "oauth_consumer_key=" + Config.QQ_APP_ID
//                        + "&access_token=" + resphone.getString("access_token")
//                        + "&openid=" + openId
//                        + "&format=" + "json";
//                new GetQQUserInfo().execute(params);
//            } catch (JSONException e) {
//                Helper.T(LoginActivity.this, "认证失败,请稍后再试-1.");
//            }
//
//        }
//
//        @Override
//        public void onError(UiError e) {
//
//        }
//
//        @Override
//        public void onCancel() {
//            Helper.T(LoginActivity.this, "取消");
//        }
//    }
//
//    /**
//     * 获取QQ用户资料
//     */
//    public class GetQQUserInfo extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String api = A.QQ_GET_USER_INFO + "?" + strings[0];
//            return Net.getApiJson(api);
//        }
//
//        @Override
//        protected void onPostExecute(String jsonString) {
//            new GetServiceUserInfo().execute(jsonString);
//        }
//    }


    public class GetServiceUserInfo extends AsyncTask<String, String, String> {
        String error = "";

        @Override
        protected String doInBackground(String... strings) {

            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(strings[0]);

                String apiString = A.GET_INFO_BY_QQ;

                String params =
                        "uname=" + jsonObject.getString("nickname")
                                + "&gender=" + jsonObject.getString("gender")
                                + "&openid=" + openId
                                + "&face=" + URLEncoder.encode(jsonObject.getString("figureurl_qq_2"));

                String request;

                try {
                    byte[] requestByte = Net.sendPostRequestByForm(apiString, params);
                    request = new String(requestByte, "UTF-8");
                } catch (Exception e) {
                    error = "网络连接失败，请检查网络是否通畅";
                    Log.i("QQ_ERROR", error);
                    request = null;
                }
                return request;
            } catch (JSONException e) {
                error = "网络连接失败，请检查网络是否通畅-1";
                Log.i("QQ_ERROR", error);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("QQ_REQUEST2", s.toString());
            userModel = new UserModel(s.toString());
            Map<String, String> userInfo = userModel.getUser();

            if (userInfo == null) {

                Helper.T(LoginActivity.this, "获取用户信息失败,请稍后再试.");
                progressDialog.dismiss();

            } else {

                UserModel.saveUser(LoginActivity.this, userInfo);
                Auth.setLogin(LoginActivity.this, true);
                Intent intent = new Intent();
                intent.putExtra("USER_INFO", s.toString());
                setResult(RESULT_FIRST_USER, intent);
                Helper.T(LoginActivity.this, "登录成功");
                progressDialog.dismiss();
                finish();
            }
        }
    }

    public void T(String msg) {
        Helper.T(this, msg);
    }

    public void L(String msg) {
        Log.i("LoginActivity", msg);
    }
}
