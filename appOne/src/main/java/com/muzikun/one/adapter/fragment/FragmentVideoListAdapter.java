package com.muzikun.one.adapter.fragment;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.data.bean.FragmentVideoListBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCBuriedPoint;
import fm.jiecao.jcvideoplayer_lib.JCBuriedPointStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by likun on 16/8/8.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class FragmentVideoListAdapter extends BaseAdapter {
    private Activity context = null;
    private List<FragmentVideoListBean> listData = null;

    public FragmentVideoListAdapter(Activity context, List<FragmentVideoListBean> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_video_list,null);
            viewHolder.vedioView = (JCVideoPlayerStandard) convertView.findViewById(R.id.item_fragment_vedio_vbox);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.vedioView.setUp(listData.get(position).VEDIO_URL
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, listData.get(position).VEDIO_TITLE);
        JCVideoPlayer.setJcBuriedPoint(new MyJCBuriedPointStandard());
        String imgUrl="";
        if(listData.get(position).IMAGE_URL!=null){
            imgUrl=(listData.get(position).IMAGE_URL).replace("www.gzbjwb.cn","wanbao.7va.cn");
        };
        Glide.with(context).load(imgUrl).into(viewHolder.vedioView.thumbImageView);
        return convertView;
    }

    private class ViewHolder{
        JCVideoPlayerStandard vedioView = null;
    }
    class MyJCBuriedPointStandard implements JCBuriedPointStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCBuriedPoint.ON_CLICK_START_ICON:
                    Log.i("Buried_Point", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_CLICK_START_ERROR:
                    Log.i("Buried_Point", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("Buried_Point", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_CLICK_PAUSE:
                    Log.i("Buried_Point", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_CLICK_RESUME:
                    Log.i("Buried_Point", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_SEEK_POSITION:
                    Log.i("Buried_Point", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_AUTO_COMPLETE:
                    Log.i("Buried_Point", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_ENTER_FULLSCREEN:
                   // Helper.setColor(context, Color.parseColor("#000000"));
                    Log.i("Buried_Point", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_QUIT_FULLSCREEN:

                //    Helper.setColor(context, Color.parseColor("#e9484d"));
                    Log.i("Buried_Point", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_ENTER_TINYSCREEN:
                    Log.i("Buried_Point", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_QUIT_TINYSCREEN:
                    Log.i("Buried_Point", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("Buried_Point", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPoint.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("Buried_Point", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCBuriedPointStandard.ON_CLICK_START_THUMB:
                    Log.i("Buried_Point", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCBuriedPointStandard.ON_CLICK_BLANK:
                    Log.i("Buried_Point", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("Buried_Point", "unknow");
                    break;
            }
        }
    }

}
