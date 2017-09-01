package com.muzikun.one.fragment.myhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.muzikun.one.R;
import com.muzikun.one.adapter.fragment.SendNewsAdapter;
import com.muzikun.one.data.bean.SendNewsBean;
import com.muzikun.one.data.config.A;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.util.EventUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 风水咨询
 * Created by likun on 16/8/9.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class SendNewsFragment extends Fragment {

    private View mainView = null;
    private PullToRefreshListView pullListView = null;
    private ListView mList;
    private Auth auth = null;
    private Map<String,String> userInfo = null;
    private SendNewsAdapter adapter;
    private List<SendNewsBean> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_listview,container,false);
        initView();
        return mainView;
    }

    private void initView() {
        pullListView = (PullToRefreshListView) mainView.findViewById(R.id.fragment_listview_view);
        mList=pullListView.getRefreshableView();
        adapter = new SendNewsAdapter(getActivity());
        mList.setAdapter(adapter);
        auth = Auth.getInstance();
        userInfo =  auth.login(getActivity());

        getQuestionsList();
    }

    private void getQuestionsList(){
        OkHttpUtils
                .get()
                .url(A.SEND_NEWS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        list.clear();
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                SendNewsBean bean = new SendNewsBean();
                                bean.id = object.getString("id");
                                bean.title = object.getString("title");
                                bean.name = object.getJSONObject("member").getString("uname");
                                bean.time = EventUtil.getFormatTime(Long.parseLong(object.getString("pubdate")));
                                bean.answerCount = object.getString("feedback_count");
                                list.add(bean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (list.size() > 0) {
                            Collections.sort(list, SendNewsBean.mAscComparator);
                            adapter.addAll(list);
                        }
                    }
                });
    }
}
