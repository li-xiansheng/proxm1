package com.muzikun.one.fragment.myhome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.muzikun.one.R;
import com.muzikun.one.activity.ArticleActivity;
import com.muzikun.one.adapter.common.CommonErrorAdapter;
import com.muzikun.one.adapter.fragment.UserStowFragmentListAdapter;
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
 * 我的收藏
 * Created by likun on 16/6/29.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class UserStowFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String TAG = "UserStowFragment";
    private View mainView = null;
    private PullToRefreshListView pullListView = null;
    private ListView mListView;
    private Map<String, String> userMap = null;
    private UserStowFragmentListAdapter listAdapter = null;
    private List<UserStowDataBean> listData = new ArrayList<>();
    private LoadingFragment loadingFragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_listview, container, false);//fragment_listview修改为fragment_listview_new
        initData();
        initView();
        loadingFragment = new LoadingFragment();
        loadingFragment.show(getFragmentManager(), "loading");
        return this.mainView;
    }

    private void initData() {
        userMap = Auth.getInstance().login(getActivity());
    }

    public void initView() {
        pullListView = (PullToRefreshListView) mainView.findViewById(R.id.fragment_listview_view);
        mListView = pullListView.getRefreshableView();
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetMyStow().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        new GetMyStow().execute();
    }


    /**
     * =======================================AsyncTask===========================================
     */

    public class GetMyStow extends AsyncTask<String, String, String> {
        public String errorMessage = null;

        @Override
        protected String doInBackground(String... strings) {

            if (userMap == null) {
                errorMessage = "请先登录！";
                return null;
            }
            String api = A.getStowList(Integer.valueOf(userMap.get(UserModel.USER_ID)));
            String params = "access_token=" + userMap.get(UserModel.USER_PASS);
            String result;

            try {
                result = new String(Net.sendPostRequestByForm(api, params), "UTF-8");
                return result;
            } catch (Exception e) {
                errorMessage = "网络请求失败，请检查网络链接是否正常";
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Helper.T(getActivity(), this.errorMessage);
                return;
            }

            try {

                JSONObject resultObject = new JSONObject(s);

                int code = resultObject.getInt("code");
                Log.i(TAG, "resultObject = " + resultObject);

                switch (code) {
                    case 1:
                        setListView(resultObject);
                        break;
                    case 0:
                        setNoDataListView();
                        break;
                    default:
                        errorMessage = "未知错误";
                        Helper.T(getActivity(), this.errorMessage);
                        return;
                }

            } catch (JSONException e) {
                this.errorMessage = "数据格式错误，请稍后再试";
                mListView.setAdapter(new CommonErrorAdapter(getActivity(), this.errorMessage, R.drawable.nodata));
                mListView.setDividerHeight(0);
                Helper.T(getActivity(), this.errorMessage);
                return;
            }
            loadingFragment.dismiss();

            if (pullListView != null && pullListView.isRefreshing()) {
                pullListView.onRefreshComplete();
            }
        }

        private void setNoDataListView() {
            mListView.setAdapter(new CommonErrorAdapter(getActivity(), "你还没有收藏呢", R.drawable.nodata));
            mListView.setDividerHeight(0);
        }

        /**
         * @param resultObject
         * @throws JSONException
         */
        private void setListView(JSONObject resultObject) throws JSONException {
            listData.clear();
            JSONArray resultArray = resultObject.getJSONArray("data");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject oneData = resultArray.getJSONObject(i);
                listData.add(new UserStowDataBean(
                        oneData.getInt("id"),
                        oneData.getInt("mid"),
                        oneData.getJSONObject("article").getInt("typeid"),
                        oneData.getInt("aid"),
                        oneData.getString("title"),
                        oneData.getString("addtime")
                ));
            }

            listAdapter = new UserStowFragmentListAdapter(getActivity(), listData);
            mListView.setAdapter(listAdapter);
            mListView.setOnItemClickListener(UserStowFragment.this);
        }


    }


    /*=======================================DATA_BEAN===========================================*/

    public class UserStowDataBean {
        public int ID;
        public int MID;
        public int TID;
        public int AID;
        public String TITLE;
        public String ADDTIME;


        public UserStowDataBean(int ID, int MID, int TID, int AID, String TITLE, String ADDTIME) {
            this.ID = ID;
            this.MID = MID;
            this.TID = TID;
            this.AID = AID;
            this.TITLE = TITLE;
            this.ADDTIME = ADDTIME;
        }
    }

    /*=========================================事件处理===========================================*/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        if (listData != null && listData.size() > 0) {
            UserStowDataBean userStowDataBean = listData.get(i - 1);//刷新头为1,故需要减1
            String articleTitle = userStowDataBean.TITLE;
            int typeId = 0;
            int articleId = 0;

            try {
                typeId = Integer.valueOf(userStowDataBean.TID);//typeid
                articleId = Integer.valueOf(userStowDataBean.AID);//articleid

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (typeId == 5) {
                ArticleActivity.startHelper(getActivity(), articleId, articleTitle, 1);
            } else {
                ArticleActivity.startHelper(getActivity(), articleId, articleTitle, 0);
            }
        }

    }


}
