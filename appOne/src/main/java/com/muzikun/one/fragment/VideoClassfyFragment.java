package com.muzikun.one.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.muzikun.one.R;
import com.muzikun.one.activity.WebVideoActivity;
import com.muzikun.one.adapter.fragment.VideoListAdapter;
import com.muzikun.one.data.bean.VideoBean;
import com.muzikun.one.data.config.A;
import com.muzikun.one.util.EventUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;


/**
 * Created by likun on 16/8/8.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class VideoClassfyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
//    ,RecyclerArrayAdapter.OnLoadMoreListener

    private String TAG = "VideoListFragment";
    private View mainView = null;
    private EasyRecyclerView listView = null;
//    private List<FragmentVideoListBean> listData = null;
//    private FragmentVideoListAdapter adapter = null;

    List<VideoBean> list = new ArrayList<>();
    VideoListAdapter adapter;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_video_classfy,container,false);
        initView();
        return mainView;
    }

    private void initView() {
        listView = (EasyRecyclerView) mainView.findViewById(R.id.recyclerView);
        adapter = new VideoListAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setErrorView(R.layout.view_error);
//        adapter.setMore(R.layout.view_more,this);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpaceDecoration itemDecoration = new SpaceDecoration(EventUtil.dip2px(getContext(), 3));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        listView.addItemDecoration(itemDecoration);
        listView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), WebVideoActivity.class);
                intent.putExtra("url",list.get(position).youku);
                intent.putExtra("title",list.get(position).title);
                startActivity(intent);
            }
        });
        getJiangZuoVideo();
    }

    private void getJiangZuoVideo(){
        OkHttpUtils
                .get()
                .url(A.getClassfyVideo(page+""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (page == 1){
                            list.clear();
                        }
                        Log.i(TAG,"getJiangZuoVideo---->"+response);
                        try {

                            JSONObject object = new JSONObject(response);
                            int total = object.getInt("total");
                            if (total > 0){
                                JSONArray array = object.getJSONArray("data");
                                for (int i =0;i<array.length();i++){
                                    JSONObject object1 = array.getJSONObject(i);
                                    VideoBean bean = new VideoBean();
                                    bean.id = object1.getString("id");
                                    bean.title = object1.getString("title");
                                    bean.youku = object1.getJSONObject("video").getString("youku");
                                    bean.time = EventUtil.getFormatTime(Long.parseLong(object1.getString("pubdate")));
                                    bean.litpic = object1.getString("litpic");
                                    list.add(bean);
                                }
                            }

                            adapter.clear();
                            Log.i(TAG,"list.size() = " + list.size());
                            if (list.size() > 0){
                                Collections.sort(list, VideoBean.mAscComparator);
                                adapter.addAll(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        page = 1;
        getJiangZuoVideo();
    }

//    @Override
//    public void onLoadMore() {
//        page ++;
//        getJiangZuoVideo();
//    }
}
