package com.muzikun.one.data.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by likun on 16/8/8.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class FragmentVideoListBean {
    public String VEDIO_URL;
    public String IMAGE_URL;
    public String VEDIO_TITLE;

    public FragmentVideoListBean(String VEDIO_URL,String IMAGE_URL, String VEDIO_TITLE) {
        this.VEDIO_URL = VEDIO_URL;
        this.IMAGE_URL = IMAGE_URL;
        this.VEDIO_TITLE = VEDIO_TITLE;
    }

    public static List<FragmentVideoListBean> createListBean(String string){
        List<FragmentVideoListBean> listBeen = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            if(jsonObject.getInt("total")<=0){
                return null;
            }

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                listBeen.add(new FragmentVideoListBean( jsonObject2.getJSONObject("video").getString("video_address2"),jsonObject2.getString("litpic"),jsonObject2.getString("title")));
            }

            return listBeen;
        } catch (JSONException e) {
            return null;
        }
    }
}
