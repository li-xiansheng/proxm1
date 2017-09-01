package com.muzikun.one.adapter.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.data.bean.PagerListBean;

import java.util.List;

/**
 * Created by likun on 16/7/26.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class PagerListAdapter extends BaseAdapter {
    private Context context;
    private List<PagerListBean> listData;

    public PagerListAdapter(Context context, List<PagerListBean> listData) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_popupwindow_fragment_page_list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.ORDER_VIEW = (TextView) view.findViewById(R.id.item_fragment_pager_popupwindow_listview_orders);
            viewHolder.TITLE_VIEW = (TextView) view.findViewById(R.id.item_fragment_pager_popupwindow_listview_title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ORDER_VIEW.setText(String.valueOf(listData.get(i).ITEM_ID));
        viewHolder.TITLE_VIEW.setText(String.valueOf(listData.get(i).TITLE));
        return view;
    }

    public class ViewHolder {
        TextView ORDER_VIEW;
        TextView TITLE_VIEW;
    }
}
