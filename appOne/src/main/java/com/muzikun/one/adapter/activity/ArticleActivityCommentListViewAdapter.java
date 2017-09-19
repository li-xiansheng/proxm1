package com.muzikun.one.adapter.activity;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.data.bean.ArticleCommentListViewBean;
import com.muzikun.one.lib.view.CircularImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by likun on 16/7/9.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class ArticleActivityCommentListViewAdapter extends BaseAdapter{
    private Context context = null;
    private List<ArticleCommentListViewBean> listData = null;

    public ArticleActivityCommentListViewAdapter(Context context, List<ArticleCommentListViewBean> listData) {
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
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_activity_article_listview_comment,viewGroup,false);
            viewHolder.USER_ICON = (CircularImage) view.findViewById(R.id.item_activity_article_listview_comment_usericon);
            viewHolder.USER_NAME = (TextView) view.findViewById(R.id.item_activity_article_listview_comment_username);
            viewHolder.TIME_STRING = (TextView) view.findViewById(R.id.item_activity_article_listview_comment_time);
            viewHolder.GOOD = (TextView) view.findViewById(R.id.item_activity_article_listview_comment_good_number);
            viewHolder.GOOD_BOX = (LinearLayout) view.findViewById(R.id.item_activity_article_listview_comment_good_box);
            viewHolder.CONTENT = (TextView) view.findViewById(R.id.item_activity_article_listview_comment_content);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        if(listData.get(i).USER_ICON == ""){
            viewHolder.USER_ICON.setImageResource(R.drawable.user_default_inc);
        }
        viewHolder.USER_NAME.setText(String.valueOf(listData.get(i).USER_NAME));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long timeString = Long.parseLong(listData.get(i).TIME_STRING)*1000;
        String time = simpleDateFormat.format(new Date(timeString));

        viewHolder.TIME_STRING.setText(time);
        viewHolder.GOOD.setText(String.valueOf(listData.get(i).GOOD));
        viewHolder.GOOD_BOX.setOnClickListener(new GoodOnClickListener(listData.get(i).COMMENT_ID));
        String imgHead="";
        if (listData.get(i).USER_ICON!=null){
            imgHead=(listData.get(i).USER_ICON).replace("www.gzbjwb.cn","wanbao.7va.cn");
        }

        Glide.with(context).load(imgHead).placeholder(R.drawable.user_default_inc).into(viewHolder.USER_ICON);
        viewHolder.CONTENT.setText(Html.fromHtml(listData.get(i).CONTENT));

        return view;
    }

    public class ViewHolder{
        public CircularImage USER_ICON;
        public TextView     USER_NAME;
        public TextView     TIME_STRING;
        public TextView     GOOD;
        public LinearLayout GOOD_BOX;
        public TextView     CONTENT;
    }

    public class GoodOnClickListener implements View.OnClickListener{

        private int commentId = 0;

        public GoodOnClickListener(int id) {
            commentId = id;
        }

        @Override
        public void onClick(View view) {
            ImageView imageView  = (ImageView) view.findViewById(R.id.item_activity_article_listview_comment_good);
            TextView textView  = (TextView) view.findViewById(R.id.item_activity_article_listview_comment_good_number);
            int oldValue =  Integer.valueOf(textView.getText().toString());
            textView.setText(String.valueOf((oldValue+1)));
            imageView.setImageResource(R.drawable.zan_select);
            view.setOnClickListener(null);
        }
    }
}
