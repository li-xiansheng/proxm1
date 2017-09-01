package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.activity.AnswerActivity;
import com.muzikun.one.data.bean.SendNewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chentao on 2017/8/3.
 */

public class SendNewsAdapter extends BaseAdapter {

    private Context context = null;

    private List<SendNewsBean> newsBeanList = new ArrayList<>();

    public SendNewsAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<SendNewsBean> list){
        this.newsBeanList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_send_news_item,parent,false);
        }
        TextView content = (TextView) convertView.findViewById(R.id.item_content);
        TextView name = (TextView) convertView.findViewById(R.id.item_name);
        TextView time = (TextView) convertView.findViewById(R.id.item_time);
        TextView answers = (TextView) convertView.findViewById(R.id.item_answers);
        TextView myAnswers = (TextView) convertView.findViewById(R.id.item_my_answers);

        content.setText(newsBeanList.get(position).title);
        name.setText(newsBeanList.get(position).name);
        time.setText(newsBeanList.get(position).time);
        answers.setText("回答数("+newsBeanList.get(position).answerCount+")");
//        myAnswers.setText(newsBeanList.get(position).myAnswerCount);

        myAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnswerActivity.class);
                intent.putExtra("id",newsBeanList.get(position).id);
                intent.putExtra("title",newsBeanList.get(position).name);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
