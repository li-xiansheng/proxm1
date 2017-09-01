package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.data.bean.ArticleListListViewBean;

import java.util.List;

/**
 * Created by leeking on 16/6/6.
 */
public class FragmentArticleListListViewAdapter extends BaseAdapter {
    private Context context                         = null;
    private List<ArticleListListViewBean> listData  = null;

    public FragmentArticleListListViewAdapter(Context context, List<ArticleListListViewBean> listData) {
        this.context                                = context;
        this.listData                               = listData;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_article_list,null);
            viewHolder = new ViewHolder();
            viewHolder.title        = (TextView) view.findViewById(     R.id.item_fragment_article_list_title);
            viewHolder.addtime      = (TextView) view.findViewById(     R.id.item_fragment_article_list_addtime);
            viewHolder.source       = (TextView) view.findViewById(     R.id.item_fragment_article_list_source);
            viewHolder.articleId    = (TextView) view.findViewById(     R.id.item_fragment_article_list_article_id);
            viewHolder.imageBox1    = (LinearLayout) view.findViewById( R.id.fragment_article_list_item_image_box1);
            viewHolder.imageBox2    = (LinearLayout) view.findViewById( R.id.fragment_article_list_item_image_box2);
            viewHolder.image0       = (ImageView) view.findViewById(    R.id.fragment_article_list_item_image0);
            viewHolder.image1       = (ImageView) view.findViewById(    R.id.fragment_article_list_item_image1);
            viewHolder.image2       = (ImageView) view.findViewById(    R.id.fragment_article_list_item_image2);
            viewHolder.image3       = (ImageView) view.findViewById(    R.id.fragment_article_list_item_image3);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder. title           .setText(listData.get(i).title);
        viewHolder. articleId       .setText(String.valueOf(listData.get(i).articleId));
        viewHolder. addtime         .setText(listData.get(i).addtime);
        viewHolder. source          .setText(listData.get(i).source);

        int picTotal                = listData.get(i).pic_total;


        if(picTotal==0){
            viewHolder.imageBox1.setVisibility(View.GONE);
            viewHolder.imageBox2.setVisibility(View.GONE);
        }else if(picTotal==1){
            viewHolder.imageBox1.setVisibility(View.VISIBLE);
            viewHolder.imageBox2.setVisibility(View.GONE);
            
            Glide.with(context)
                    .load(listData.get(i).pic[0])
                    .crossFade()
                    .into(viewHolder.image0);




        }else if(picTotal==3){
            viewHolder.imageBox1.setVisibility(View.GONE);
            viewHolder.imageBox2.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(listData.get(i).pic[0])
                    .crossFade()
                    .into(viewHolder.image1);
            Glide.with(context)
                    .load(listData.get(i).pic[1])
                    .crossFade()
                    .into(viewHolder.image2);
            Glide.with(context)
                    .load(listData.get(i).pic[2])
                    .crossFade()
                    .into(viewHolder.image3);
        }


        return view;
    }

    private class ViewHolder {
        public TextView     title;
        public TextView     addtime;
        public TextView     source;
        public TextView     articleId;
        public LinearLayout imageBox1;
        public ImageView    image0;
        public LinearLayout imageBox2;
        public ImageView    image1;
        public ImageView    image2;
        public ImageView    image3;



    }
}
