package com.muzikun.one.fragment.news;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.muzikun.one.R;
import com.muzikun.one.activity.ArticleActivity;
import com.muzikun.one.adapter.common.CommonErrorAdapter;
import com.muzikun.one.adapter.fragment.FragmentArticleListDefaultAdapter;
import com.muzikun.one.adapter.fragment.FragmentArticleListListViewAdapter;
import com.muzikun.one.data.bean.ArticleListListViewBean;
import com.muzikun.one.lib.util.ACache;
import com.muzikun.one.lib.util.Net;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by leeking on 16/6/1.
 */
public class ArticleListFragment extends Fragment {
    private View                            mainView = null;
    private PullToRefreshListView           listView = null;
    private FragmentArticleListDefaultAdapter defaultAdapter =  null;
    private FragmentArticleListListViewAdapter articleListViewAdapter =  null;
    private  int                            id = 0;
    private  int                            page = 0;
    protected boolean                       isVisible;
    public WaitLoad                         waitLoad = null;
    private List<ArticleListListViewBean>   listData = null;
    private Handler                         handler = null;
    private ACache aCache = null;
    private int windowType                             =0;
    private TextView listFooterView                 = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_article_list,container,false);
        initView();

        return mainView;
    }

    public void initView(){
        listView            = (PullToRefreshListView) mainView.findViewById(R.id.fragment_article_list_listview);
        aCache              = ACache.get(getActivity());
        defaultAdapter      = new FragmentArticleListDefaultAdapter(getActivity(),1);



        listView.setAdapter(defaultAdapter);
        listView.setBackgroundColor(Color.parseColor("#f7f7fe"));
        listView.setOnRefreshListener(new ListViewPullToRefreshListener());
        listView.setOnScrollListener(new ArticleListOnScrollChangeListener());
        handler = new WaitLoadHanlder();

        try {
            id = getArguments().getInt("id");
        }catch (Exception e){
            id = 0;
        }
        try{
            windowType = getArguments().getInt("windowType");
        }catch (Exception e){
            windowType = 0;
        }
        if(id==0){
            startLoad();
        }
    }


    @Override
    public void onResume() {

     //   System.out.println("onResume:第" + String.valueOf(id+1) + "个");
        super.onPause();
    }

    @Override
    public void onStart() {
       // System.out.println("onStart:第" + String.valueOf(id+1) + "个");
        super.onStart();
    }

    @Override
    public void onStop() {
        try {
            waitLoad.isload = false;
        }catch (Exception e){

        }
        super.onStop();
    }

    public void startLoad(){
        waitLoad = new WaitLoad();
        waitLoad.isload =true;
        waitLoad.start();

    }

    public class WaitStop extends Thread{
        @Override
        public void run() {
            try {
                sleep(2000);
            } catch (InterruptedException e) {

            }
            try {
                Looper.prepare();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","stop");
                message.setData(bundle);
                handler.sendMessage(message);
                Looper.loop();
            }catch (Exception e){

            }
        }
    }

    public class WaitLoad extends Thread{
        public boolean isload = true;
        @Override
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }

            if(!isHidden()) {
                try {
                    Looper.prepare();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "start");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    Looper.loop();
                }catch (Exception e){

                }
            }
        }
    }

    public class GetArticleList extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            String jsonString ;
            jsonString = Net.getApiJson(strings[0]);
            aCache.put(strings[0],jsonString,1* ACache.TIME_HOUR);

            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            setListView(s,true);
        }
    }

    public class ListViewOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView idText = (TextView)view.findViewById(R.id.item_fragment_article_list_article_id);
            TextView titleText = (TextView) view.findViewById(R.id.item_fragment_article_list_title);
            int articleId ;
            String articleTitle ;
            try {
                articleId = Integer.valueOf(idText.getText().toString());
                articleTitle = titleText.getText().toString();
            }catch (Exception e){
                articleId = 0;
                articleTitle = "未知文章";
            }
            if(id==5){
                ArticleActivity.startHelper(getActivity(),articleId,articleTitle,1);
            }else{
                ArticleActivity.startHelper(getActivity(),articleId,articleTitle,0);
            }
        }
    }

    public void startWaitStop(){
        new WaitStop().start();
    }

    public List<ArticleListListViewBean> jsonToData(String jsonString) throws JSONException {
        List<ArticleListListViewBean> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0 ; i < jsonArray.length() ; i ++ ) {

            int picTotal = jsonArray.getJSONObject(i).getInt("pic_total");
            String[] pic ;
            if (picTotal==0){
                pic = new String[0];
            }else{
                int length = jsonArray.getJSONObject(i).getJSONArray("pic").length();
                pic = new String[length];
                for(int x = 0 ; x < length ; x ++){
                    pic[x] = jsonArray.getJSONObject(i).getJSONArray("pic").getString(x);
                }
            }
            list.add(new ArticleListListViewBean(
                    jsonArray.getJSONObject(i).getString("title"),
                    jsonArray.getJSONObject(i).getString("pubdate"),
                    jsonArray.getJSONObject(i).getString("source"),
                    pic,
                    picTotal,
                    jsonArray.getJSONObject(i).getInt("id")
            ));
        }
        return list;
    }

    public void setListView(String s ,boolean add){
        if(s=="error" || s=="" || s==null){
            listView.onRefreshComplete();
            if (add){
                CommonErrorAdapter errorAdapter = new CommonErrorAdapter(getActivity(),"没有数据",R.drawable.nonews);
                listView.setAdapter(errorAdapter);
            }else {
                listFooterView.setText("没有数据啦");
                Toast.makeText(getActivity(), "没有数据啦", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        try {
            if(add) {

                listData = jsonToData(s);
                articleListViewAdapter = new FragmentArticleListListViewAdapter(getActivity(), listData);
                listView.setAdapter(articleListViewAdapter);
                listView.setBackgroundColor(Color.parseColor("#ffffff"));
                listView.setOnItemClickListener(new ListViewOnItemClickListener());
                listView.setOnScrollListener(new ArticleListOnScrollChangeListener());
                page = 1;
            }else{
                try{
                    listData.addAll(jsonToData(s));
                    articleListViewAdapter.notifyDataSetChanged();
                    page++;
                }catch (Exception e){
                    Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
                }

            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
        }
        listView.onRefreshComplete();
        try {
            waitLoad.isload = false;
        }catch (Exception e){

        }
    }

    class ListViewPullToRefreshListener implements PullToRefreshBase.OnRefreshListener{

        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            String api = "http://wanbao.7va.cn/api/getArticleList.php" + "?type=" + id;
            new GetArticleList().execute(api);
        }
    }

    class WaitLoadHanlder extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (bundle.getString("type")){
                case "start":
                    String api = "http://wanbao.7va.cn/api/getArticleList.php" + "?type=" + id;
                    try {
                        String jsonString2 = aCache.getAsString(api);
                        if(jsonString2.length()<10){
                            listView.setRefreshing();
                        }else{
                            setListView(jsonString2,true);
                        }
                    }catch (Exception e){
                        listView.setRefreshing();
                    }

                    break;
                case "stop":
                    listView.onRefreshComplete();
                    break;
                default:

            }

            super.handleMessage(msg);
        }
    }

    public class ArticleListOnScrollChangeListener implements AbsListView.OnScrollListener{
        private int nowFirstPosition = 0;
        private AbsListView absListView ;
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            addListFooter();

            this.absListView = absListView;
            nowFirstPosition = absListView.getFirstVisiblePosition();
            Log.i("LastVisiblePosition:",String.valueOf(absListView.getLastVisiblePosition()));
            Log.i("getCount:",String.valueOf((absListView.getCount() -1)));
            if ((absListView.getLastVisiblePosition()+5) >= (absListView.getCount() -1)) {

                if (scrollState==0) {
//                    http://wanbao.7va.cn/api/getArticleList.php?type=1&page=1
//                    http://www.gzbjwb.cn/api/getArticleList.php
                    String api = "http://wanbao.7va.cn/api/getArticleList.php" + "?type=" + id + "&page=" + page;
                    new AsyncTask<String,String,String>(){

                        @Override
                        protected String doInBackground(String... strings) {
                            return Net.getApiJson(strings[0]);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            if(ArticleListOnScrollChangeListener.this.absListView.getLastVisiblePosition() == (ArticleListOnScrollChangeListener.this.absListView.getCount() -1)){

                            }

                            Log.i("jsonString",s);
                            setListView(s,false);
                        }
                    }.execute(api);
                }
            }

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        }
    }


    public void addListFooter(){
        if(listFooterView==null) {
            listFooterView = new TextView(getActivity());
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            listFooterView.setLayoutParams(layoutParams);
            listFooterView.setText("正在加载...");
            listFooterView.setGravity(Gravity.CENTER);
            listFooterView.setPadding(38,38,38,38);
            listFooterView.setTextSize(14f);
            listFooterView.setTextColor(Color.parseColor("#AAAAAA"));
            listFooterView.setBackgroundColor(Color.parseColor("#fafafa"));
            listView.getRefreshableView().addFooterView(listFooterView);
        }else{
            listFooterView.setText("正在加载...");
        }

    }
}
