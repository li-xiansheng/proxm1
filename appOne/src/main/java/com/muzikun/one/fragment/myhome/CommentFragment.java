package com.muzikun.one.fragment.myhome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.muzikun.one.R;
import com.muzikun.one.adapter.common.CommonErrorAdapter;
import com.muzikun.one.adapter.fragment.UserCommentListViewAdapter;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.fragment.common.LoadingFragment;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的评论
 * Created by likun on 16/6/30.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class CommentFragment extends Fragment {
    private View mainView       = null;
    private PullToRefreshListView listView   = null;
    private List<CommentDataBean> dataBeenList = null;
    private Auth auth = null;
    private Map<String,String> userInfo = null;
    private List<CommentDataBean> listData = null;
    private UserCommentListViewAdapter adapter = null;
    private LoadingFragment loadingFragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.from(getActivity()).inflate(R.layout.fragment_listview,container,false);
        loadingFragment = new LoadingFragment();
        loadingFragment.show(getFragmentManager(),"loading");
        initData();
        initView();
        return mainView;
    }

    public void initData(){
        dataBeenList = new ArrayList<>();

    }

    private void initView() {
        listView = (PullToRefreshListView) mainView.findViewById(R.id.fragment_listview_view);
        auth = Auth.getInstance();
        userInfo = auth.login(getActivity());
        if(userInfo == null){
            Helper.T(getActivity(),"你没有登录，请先登录");
            getActivity().finish();
            return;
        }
        new GetComment().execute();
    }


    public class GetComment extends AsyncTask<String,String,String>{
        public String errorMessage = "未知错误";
        @Override
        protected String doInBackground(String... strings) {
            String params = "mid=" + String.valueOf(userInfo.get(UserModel.USER_ID))
                            + "&access_token=" + userInfo.get(UserModel.USER_PASS);
            String api = A.USER_COMMENT;
            String result;
            try {
                result = new String(Net.sendPostRequestByForm(api,params),"UTF-8");
            } catch (Exception e) {
                errorMessage = "网络连接失败，请稍后再试";
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                Helper.T(getActivity(),errorMessage);
                loadingFragment.dismiss();
                listView.setAdapter(new CommonErrorAdapter(getActivity(),errorMessage,R.drawable.nodata));
                listView.getRefreshableView().setDividerHeight(0);
                return;
            }

            try {
                JSONObject resultObject = new JSONObject(s);
                if(resultObject.getInt("code")==1){
                    JSONArray commentArrayJson = resultObject.getJSONArray("data");
                    listData = new ArrayList<>();
                    for (int i = 0; i < commentArrayJson.length(); i++) {
                        JSONObject data = commentArrayJson.getJSONObject(i);
                        listData.add(new CommentDataBean(
                                    userInfo.get(UserModel.USER_NAME),
                                    data.getString("arctitle"),
                                    data.getString("msg"),
                                    data.getString("dtime"),
                                    userInfo.get(UserModel.USER_ICON)
                                ));
                    }

                    adapter = new UserCommentListViewAdapter(getActivity(),listData);
                    listView.setAdapter(adapter);
                }else{
                    errorMessage = resultObject.getString("data");
                    Helper.T(getActivity(),errorMessage);
                    listView.setAdapter(new CommonErrorAdapter(getActivity(),this.errorMessage,R.drawable.nodata));
                    listView.getRefreshableView().setDividerHeight(0);
                }
            } catch (JSONException e) {
                errorMessage = "没有数据";
                Helper.T(getActivity(),errorMessage);
                listView.setAdapter(new CommonErrorAdapter(getActivity(),this.errorMessage,R.drawable.nodata));
                listView.getRefreshableView().setDividerHeight(0);
            }finally {
                loadingFragment.dismiss();
            }
            Log.i("Comment_result",s);
        }
    }


    public class CommentDataBean{
        public String USER_NAME;
        public String ARTICLE_TITLE;
        public String CONTENT;
        public String ADD_TIME;
        public String FACE;

        public CommentDataBean(String USER_NAME, String ARTICLE_TITLE, String CONTENT, String ADD_TIME, String FACE) {
            this.USER_NAME = USER_NAME;
            this.ARTICLE_TITLE = ARTICLE_TITLE;
            this.CONTENT = CONTENT;
            this.ADD_TIME = ADD_TIME;
            this.FACE = FACE;
        }
    }


}
