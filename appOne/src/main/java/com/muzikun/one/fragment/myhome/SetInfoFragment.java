package com.muzikun.one.fragment.myhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.fragment.common.LoadingFragment;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;
import com.muzikun.one.lib.util.UploadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by likun on 16/9/4.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class SetInfoFragment extends Fragment implements View.OnClickListener{
    private Context context = null;
    private View mainView = null;
    private EditText userNameView = null;
    private ImageView userIconView = null;
    private Button userNameButton = null;
    private LinearLayout userIconBox = null;
    private Map<String,String> userInfo = null;
    private LoadingFragment loadingFragment = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.from(getContext()).inflate(R.layout.fragment_setinfo,container,false);
        initView();
        return mainView;
    }

    private void initView() {
        userNameView = (EditText) mainView.findViewById(R.id.fragment_setinfo_username_textview);
        userIconView = (ImageView) mainView.findViewById(R.id.fragment_setinfo_usericon_imageview);
        userNameButton = (Button) mainView.findViewById(R.id.fragment_setinfo_username_submit);
        userIconBox = (LinearLayout)mainView.findViewById(R.id.fragment_setinfo_usericon_box);

        loadingFragment = new LoadingFragment();

        if(!Auth.isLogin(getActivity())){
            getActivity().finish();
            return;
        }

        userInfo = Auth.getInstance().login(getActivity());


        userNameView.setText(userInfo.get(UserModel.USER_NAME));
        String imgUrl="";
        if(userInfo.get(UserModel.USER_ICON)!=null){
            imgUrl=(userInfo.get(UserModel.USER_ICON)).replace("www.gzbjwb.cn","wanbao.7va.cn");
        }
        Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.mine_head).into(userIconView);


        userIconBox.setOnClickListener(this);
        userNameButton.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            loadingFragment.show(getFragmentManager(),"loading");
            new UpUserIcon().execute(picturePath);
            Log.i("result",picturePath);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fragment_setinfo_usericon_box) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, getActivity().CONTEXT_RESTRICTED);

        } else if (i == R.id.fragment_setinfo_username_submit) {
            String newName = userNameView.getText().toString();
            new SetUserName().execute(newName);

        } else {
        }
    }

    public class UpUserIcon extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... p) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put("mid", userInfo.get(UserModel.USER_ID));
            params.put("access_token", userInfo.get(UserModel.USER_PASS));
            final Map<String, File> files = new HashMap<String, File>();
            File file = new File(p[0]);
            files.put("uploadfile", file);
            String request;
            try {
                request = UploadUtil.post(A.SET_USER_ICON, params, files);
            } catch (IOException e) {
                request = null;
            }
            return request;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.getInt("code") == 1){
                    String imgUrl="";
                    if(jsonObject.getString("data")!=null){
                        imgUrl=(jsonObject.getString("data")).replace("www.gzbjwb.cn","wanbao.7va.cn");
                    }

                    Glide.with(getActivity()).load(imgUrl).into(userIconView);
                    SharedPreferences userShared = UserModel.getSharedPreferences(SetInfoFragment.this.getContext());
                    SharedPreferences.Editor edit  =  userShared.edit();
                    edit.putString(UserModel.USER_ICON,jsonObject.getString("data"));
                    edit.commit();
                }
                Helper.T(getActivity(),jsonObject.getString("msg"));

            } catch (JSONException e) {
                Helper.T(getActivity(),"上传失败，请稍后再试-1");
            }


            loadingFragment.dismiss();
        }
    }


    public class SetUserName extends AsyncTask<String,String,String>{
        private  String name;
        @Override
        protected String doInBackground(String... p) {
            name = p[0];
            String params = "mid=" + userInfo.get(UserModel.USER_ID) + "&access_token=" + userInfo.get(UserModel.USER_PASS)
                    + "&value="+name;
            String result ;

            try {
                result = new String(Net.sendPostRequestByForm(A.SET_USER_NAME,params),"UTF8");
            } catch (Exception e) {
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject js = new JSONObject(s);
                if(js.getInt("code") == 1){
                    SharedPreferences userShared = UserModel.getSharedPreferences(SetInfoFragment.this.getContext());
                    SharedPreferences.Editor edit  =  userShared.edit();
                    edit.putString(UserModel.USER_NAME,name);
                    edit.commit();
                }
                Helper.T(getActivity(),js.getString("msg"));
            } catch (JSONException e) {
                Helper.T(getActivity(),"修改失败，请稍后再试");
            }
        }
    }
}
