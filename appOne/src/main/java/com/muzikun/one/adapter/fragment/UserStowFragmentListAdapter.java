package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.fragment.myhome.UserStowFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by likun on 16/7/21.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class UserStowFragmentListAdapter extends BaseAdapter {

    private Context context = null;
    private List<UserStowFragment.UserStowDataBean> listData = null;

    public UserStowFragmentListAdapter(Context context, List<UserStowFragment.UserStowDataBean> listData) {
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
        if(view==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_userstow_listview,viewGroup,false);
            viewHolder.titleView = (TextView) view.findViewById(R.id.item_fragment_userstow_listview_title);
//            viewHolder.articleId = (TextView) view.findViewById(R.id.item_fragment_userstow_listview_articleid);
//            viewHolder.typeId = (TextView) view.findViewById(R.id.item_fragment_userstow_listview_typeid);
            viewHolder.addTimeView = (TextView) view.findViewById(R.id.item_fragment_userstow_listview_addtime);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.titleView.setText(listData.get(i).TITLE);
//        viewHolder.articleId.setText(String.valueOf(listData.get(i).AID));
//        viewHolder.typeId.setText(String.valueOf(listData.get(i).TID));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timeNumber = Long.parseLong(listData.get(i).ADDTIME) *1000;
        String timeString =  simpleDateFormat.format(new Date(timeNumber));
        viewHolder.addTimeView.setText(timeString);

        return view;
    }


    private class ViewHolder{
       public TextView titleView;
//       public TextView articleId;
//       public TextView typeId;
        public TextView addTimeView;
    }

}
