package com.muzikun.one.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.muzikun.one.R;
import com.muzikun.one.data.config.A;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by likun on 16/6/23.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class RegisterActivity extends FragmentActivity implements View.OnClickListener{
    private EditText phoneNumberView = null;
    private EditText smsCodeView = null;
    private EditText passwordView = null;
    private Button sendSmsCodeView = null;
    private Button registerButton = null;
    private LinearLayout backButton = null;
    private String phoneNumber = null;
    private String phoneCode = null;
    private String password     = null;
    private String smsCode = null;
//    private LoadingFragment loadingFragment = null;
    private Handler handler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        Helper.setColor(this, Color.parseColor("#891C21"));
        initView();
    }

    private void initView() {
        phoneNumberView     = (EditText) findViewById(R.id.activity_register_userphone);
        smsCodeView          = (EditText) findViewById(R.id.activity_register_usercode);
        passwordView          = (EditText) findViewById(R.id.activity_register_userpass);
        sendSmsCodeView     = (Button) findViewById(R.id.activity_register_sendsms);
        registerButton          = (Button) findViewById(R.id.activity_register_registerbutton);
        backButton          = (LinearLayout) findViewById(R.id.activity_register_back);

//        loadingFragment = new LoadingFragment();

        backButton          .setOnClickListener(this);
        sendSmsCodeView     .setOnClickListener(this);
        registerButton          .setOnClickListener(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int time = msg.getData().getInt("time");
                if(time != 0){
                    sendSmsCodeView.setTextColor(Color.parseColor("#DDDDDD"));
                    sendSmsCodeView.setText("请在"+String.valueOf(time)+"后再次请求");
                }else{
                    sendSmsCodeView.setTextColor(Color.parseColor("#e9484d"));
                    sendSmsCodeView.setText("点此获取验证码");
                    sendSmsCodeView     .setOnClickListener(RegisterActivity.this);
                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.activity_register_back) {
            finish();

        } else if (i == R.id.activity_register_sendsms) {
            sendSms();

        } else if (i == R.id.activity_register_registerbutton) {
            next();

        } else {
        }
    }


    private void sendSms() {
        phoneNumber = phoneNumberView.getText().toString();
        phoneCode = Helper.getPhoneCode(this);
        if(phoneNumber.length()<11){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("提示");
            alertDialogBuilder.setMessage("请输入11位数字的手机号码");
            alertDialogBuilder.show();
            return;
        }
        String paramsPost = "phone=" + phoneNumber + "&phone_code="+phoneCode;
//        loadingFragment.show(getSupportFragmentManager(),"loading");
        new SendSmsCodeRequest().execute(paramsPost);
    }

    class SendSmsCodeRequest extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String resultString = null;
            try {
                byte[] result = Net.sendPostRequestByForm(A.SEND_SMS_CODE,params[0]);
                resultString = new String(result,"UTF8");
            } catch (Exception e) {
                resultString = null;
            }
            return resultString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("sendsmsresult",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int resultCode = jsonObject.getInt("code");
                if(resultCode == 0){
                    sendSmsCodeView.setOnClickListener(null);
                    new TimeRun().start();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialogBuilder.setTitle("提示");
                    alertDialogBuilder.setMessage("发送成功，请查收");
                    alertDialogBuilder.show();

                }else{
                    if(resultCode==22) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                        alertDialogBuilder.setTitle("提示");
                        alertDialogBuilder.setMessage("单个号码一个小时只能申请三条短信，请稍后再试");
                        alertDialogBuilder.show();
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                        alertDialogBuilder.setTitle("提示");
                        alertDialogBuilder.setMessage(jsonObject.getString("msg"));
                        alertDialogBuilder.show();
                    }

                }
            } catch (JSONException e) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                alertDialogBuilder.setTitle("提示");
                alertDialogBuilder.setMessage("获取失败，请稍后再试。。。");
                alertDialogBuilder.show();
            }
//            loadingFragment.dismiss();
        }

    }


    private class TimeRun extends Thread{
        private int time = 60;
        @Override
        public void run() {

            while (time>=0){
                try {
                    sleep(1000);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("time",time);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    time --;
                } catch (InterruptedException e) {
                    time = 0;
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("time",time);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    break;
                }

            }
        }
    }


    private void next() {
        password = passwordView.getText().toString();
        phoneNumber = phoneNumberView.getText().toString();
        smsCode = smsCodeView.getText().toString();
        phoneCode = Helper.getPhoneCode(this);

        if(phoneNumber.length()!= 11){
            Helper.T(this,"手机号必须是11位数字");
            return;
        }

        if(password.length() < 6 || password.length() > 16){
            Helper.T(this,"密码长度必须在6到16位之间");
            return;
        }

        if(smsCode.length()!=6){
            Helper.T(this,"请输入正确的验证码");
            return;
        }

        String params = "userphone=" + phoneNumber + "&smscode=" + smsCode + "&userpass=" + password

                + "&phonecode=" + phoneCode ;
//        loadingFragment.show(getSupportFragmentManager(),"loading");
        new SendUserPass().execute(params);

    }


    public class SendUserPass extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            try {
                result =  new String(Net.sendPostRequestByForm(A.SEND_USER_PASS,params[0]),"UTF8");
            } catch (Exception e) {
                result = null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            L(s);
//            loadingFragment.dismiss();
            if(s != null){
                try {
                    JSONObject body = new JSONObject(s);
                    int resultCode = body.getInt("code");
                    if(resultCode == 1){
                        T(body.getString("data"));
                        Log.i("result",body.getString("data"));
                        finish();
                        return;
                    }else{
                        T(body.getString("data"));
                        return;
                    }
                } catch (JSONException e) {
                    T("提交失败，请稍后再试");
                    return;
                }
            }else{

               L("空");
                Helper.T(RegisterActivity.this,"注册失败，请稍后再试");
            }
        }


    }

    private void T(String msg){
        Helper.T(RegisterActivity.this,msg);
    }
    private void L(String msg){
        Log.i("RegisterActivity",msg);
    }

}
