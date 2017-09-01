package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.data.bean.MessageDataBean;

import java.util.List;

/**
 * Created by likun on 16/6/27.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class FragmentMessageListViewAdapter extends BaseAdapter{
    private Context context = null;
    private List<MessageDataBean> listData = null;

    public FragmentMessageListViewAdapter(Context context, List<MessageDataBean> listData) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_listview_view,viewGroup,false);
            viewHolder.titleView = (TextView) view.findViewById(R.id.item_activity_message_listview_title);
            viewHolder.description = (TextView) view.findViewById(R.id.item_activity_message_listview_description);
            viewHolder.timeView = (TextView) view.findViewById(R.id.item_activity_message_listview_time);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_activity_message_listview_image);
            view.setTag(viewHolder);
        }else{
            viewHolder =   (ViewHolder) view .getTag();
        }
        viewHolder.titleView.setText(listData.get(i).TITLE);
        viewHolder.description.setText(listData.get(i).DESCRIPTION);
        viewHolder.timeView.setText(listData.get(i).TIME);
        viewHolder.imageView.setImageResource(listData.get(i).IMAGE);
        return view;
    }


    private class ViewHolder {
        ImageView imageView  = null ;
        TextView titleView = null;
        TextView description = null;
        TextView timeView = null;
    }

}
