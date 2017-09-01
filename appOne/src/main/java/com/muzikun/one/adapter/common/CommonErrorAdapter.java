package com.muzikun.one.adapter.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.muzikun.one.R;

/**
 * Created by likun on 16/7/15.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class CommonErrorAdapter extends BaseAdapter{


    private Context context = null;
    private String message  = "";
    private int image;

    public CommonErrorAdapter(Context context, String message , int image) {
        this.context = context;
        this.message = message;
        this.image   = image;
    }
    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_nodata,viewGroup,false);
        }
        TextView textView = (TextView) view.findViewById(R.id.item_listview_nodata_message);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_listview_nodata_image);
        textView.setText(message);
        imageView.setImageResource(image);

        return view;
    }
}
