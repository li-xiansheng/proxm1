package com.muzikun.one.fragment.myhome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muzikun.one.R;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * Created by likun on 16/6/29.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class SendArticleFragment extends Fragment {

    private View mainView = null;
    private EditText titleView = null;
    private EditText contentView = null;
    private EditText phoneView = null;
    private Button button = null;
    private Auth auth = null;
    private Map<String,String> userInfo = null;
    private String title = null;
    private String content = null;
    private String phone = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.from(getActivity()).inflate(R.layout.fragment_send_article,container,false);
        initView();
        return this.mainView;
    }

    private void initView() {
        titleView = (EditText) mainView.findViewById(R.id.fragment_send_article_title);
        contentView = (EditText) mainView.findViewById(R.id.fragment_send_article_content);
        phoneView = (EditText) mainView.findViewById(R.id.fragment_send_article_phone);
        button = (Button) mainView.findViewById(R.id.fragment_send_article_button);
        auth = Auth.getInstance();
        userInfo =  auth.login(getActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleView.getText().toString();
                String content = contentView.getText().toString();
                String phone = phoneView.getText().toString();
                if(title.length()<2){
                    Toast.makeText(getActivity(),"请认真填写标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(content.length()<2){
                    Toast.makeText(getActivity(),"请填认真填写内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.length() != 11){
                    Toast.makeText(getActivity(),"请填写11位数字的手机号" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                SendArticleFragment.this.title = title;
                SendArticleFragment.this.content = content;
                SendArticleFragment.this.phone = phone;
                new SendArticleContent().execute();
            }
        });

    }

    class SendArticleContent extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            String request = "title="+title+"&content="+content+"&userphone="+phone+"&mid="+userInfo.get(UserModel.USER_ID)+ "&access_token=" + userInfo.get(UserModel.USER_PASS);
            String result = null;
            try {
                 result = new String(Net.sendPostRequestByForm(A.ADD_ARTICLE,request),"UTF8");
            } catch (Exception e) {
                result =null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("result",s);
            if(s!=null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int total  = jsonObject.getInt("code");
                    if(total==0){
                        Toast.makeText(getActivity(),"提交失败，请稍后再试3",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"成功！请退出后刷新查看",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(getActivity(),"提交失败，请稍后再试1",Toast.LENGTH_SHORT).show();
                }
                Log.i("result",s);
            }else{
                Toast.makeText(getActivity(),"提交失败，请稍后再试",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
