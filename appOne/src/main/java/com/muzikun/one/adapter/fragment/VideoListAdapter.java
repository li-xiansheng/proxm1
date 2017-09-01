package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.muzikun.one.data.bean.VideoBean;
import com.muzikun.one.viewholder.VideoListViewHolder;

/**
 * Created by chentao on 2017/8/3.
 */

public class VideoListAdapter extends RecyclerArrayAdapter<VideoBean> {

    public VideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(parent);
    }

}

//public class VideoListAdapter extends BaseAdapter {
//
//    private Context context = null;
//
//    private List<VideoBean> newsBeanList = new ArrayList<>();
//
//    public VideoListAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void addAll(List<VideoBean> list){
//        this.newsBeanList = list;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return newsBeanList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return newsBeanList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        if(convertView == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_video_item,parent,false);
//        }
//        ImageView content = (ImageView) convertView.findViewById(R.id.imageView);
//
//        Glide.with(context)
//                .load(newsBeanList.get(position).litpic)
////                .placeholder(R.drawable.test_video)
//                .into(content);
//
//        return convertView;
//    }
//}
