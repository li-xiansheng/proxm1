package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.fragment.myhome.CommentFragment;
import com.muzikun.one.lib.view.CircularImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by likun on 16/7/25.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class UserCommentListViewAdapter extends BaseAdapter {
    private Context context = null;
    private List<CommentFragment.CommentDataBean> listData = null;

    public UserCommentListViewAdapter(Context context, List<CommentFragment.CommentDataBean> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_comment_list,null);
            viewHolder = new ViewHolder();
            viewHolder.USER_ICON = (CircularImage) view.findViewById(R.id.item_fragment_comment_listview_comment_usericon);
            viewHolder.USER_NAME = (TextView) view.findViewById(R.id.item_fragment_comment_listview_comment_username);
            viewHolder.TIME_STRING = (TextView) view.findViewById(R.id.item_fragment_comment_listview_comment_time);
            viewHolder.CONTENT = (TextView) view.findViewById(R.id.item_fragment_comment_listview_comment_content);
            viewHolder.ARTICLE_TITLE = (TextView) view.findViewById(R.id.item_fragment_comment_listview_comment_title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        CommentFragment.CommentDataBean commentDataBean = listData.get(i);
        Glide.with(context).load(commentDataBean.FACE).into(viewHolder.USER_ICON);
        viewHolder.USER_NAME.setText(commentDataBean.USER_NAME);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long timeNumber = Long.parseLong(commentDataBean.ADD_TIME) *1000;
        String timeString =  simpleDateFormat.format(new Date(timeNumber));
        viewHolder.TIME_STRING.setText(timeString);
        viewHolder.CONTENT.setText(commentDataBean.CONTENT);
        viewHolder.ARTICLE_TITLE.setText("标题："+commentDataBean.ARTICLE_TITLE);
        return view;
    }

    private class ViewHolder {
        public CircularImage USER_ICON;
        public TextView     USER_NAME;
        public TextView     TIME_STRING;
        public TextView     CONTENT;
        public TextView     ARTICLE_TITLE;
    }

}
