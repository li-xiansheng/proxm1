package com.muzikun.one.fragment.myhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.muzikun.one.R;
import com.muzikun.one.adapter.common.CommonErrorAdapter;
import com.muzikun.one.adapter.fragment.FragmentMessageListViewAdapter;
import com.muzikun.one.data.bean.MessageDataBean;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.fragment.common.LoadingFragment;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by likun on 16/6/28.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View mainView = null;
    private PullToRefreshListView pullListView = null;
    private ListView mListView;
    private List<MessageDataBean> listDataBean = null;
    private FragmentMessageListViewAdapter fragmentMessageListViewAdapter = null;
    private LoadingFragment loadingFragment =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView =inflater.inflate(R.layout.fragment_listview,container,false);//fragment_listview

        initData();

        initView();

        loadingFragment = new LoadingFragment();
        loadingFragment.show(getFragmentManager(),"test");

        return mainView;

    }


    private void initData() {
        listDataBean = new ArrayList<>();
    }

    private void initView() {
        this.pullListView = (PullToRefreshListView) mainView.findViewById(R.id.fragment_listview_view);
        mListView=pullListView.getRefreshableView();
        Intent intent = getActivity().getIntent();
        try {
            new GetUserMessage().execute(intent.getStringExtra(UserModel.USER_PHONE), intent.getStringExtra(UserModel.USER_PASS));
        }catch (Exception e){
            getActivity().finish();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public class GetUserMessage extends AsyncTask<String,String,String> {
        private String errorMessage ;

        @Override
        protected String doInBackground(String... strings) {
            String params = "userphone=" + strings[0] + "&userpass=" + strings[1];
            String result ;
            try {
                result =  new String(Net.sendPostRequestByForm(A.getUserMessage(),params),"utf8");
            } catch (Exception e) {
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Helper.T(getActivity(),"网络连接失败,请稍后再试-1");
                mListView.setAdapter(new CommonErrorAdapter(getActivity(),"网络连接失败,请稍后再试-1",R.drawable.nomessage));
                mListView.setDividerHeight(0);
            }else{
                listDataBean = jsonToData(s);
                if(listDataBean==null){
                    mListView.setAdapter(new CommonErrorAdapter(getActivity(),errorMessage,R.drawable.nomessage));
                    mListView.setDividerHeight(0);
                }else{
                    fragmentMessageListViewAdapter = new FragmentMessageListViewAdapter(getActivity(),listDataBean);
                    mListView.setAdapter(fragmentMessageListViewAdapter);
                }
            }
            loadingFragment.dismiss();
        }


        public List<MessageDataBean> jsonToData(String string){
            try {
                List<MessageDataBean> messageDataBeanList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(string);
                if(jsonObject.getInt("code") != 1){
                    if(jsonObject.getInt("code") == 0){
                        errorMessage = "没有数据";
                    }else if(jsonObject.getInt("code") == -1){
                        errorMessage = "没有数据";
                    }
                    return null;
                }else{
                    JSONArray dataList = jsonObject.getJSONArray("data");
                    for (int i = 0; i < dataList.length(); i++) {

                        messageDataBeanList.add(new MessageDataBean(
                                "你收到一条新的短消息",
                                dataList.getJSONObject(i).getString("subject"),
                                dataList.getJSONObject(i).getString("sendtime"),
                                R.drawable.message_icon
                        ));
                    }
                    return messageDataBeanList;
                }
            } catch (JSONException e) {
                this.errorMessage = "获取数据失败,请稍后再试 -3";
                return null;
            }
        }

    }
}
