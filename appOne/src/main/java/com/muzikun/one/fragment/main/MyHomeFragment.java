package com.muzikun.one.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.activity.ShowListActivity;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leeking on 16/6/15.
 */
public class MyHomeFragment  extends Fragment implements View.OnClickListener {
    private View mainView                       = null;
    private ListView navigationList             = null;
    private SimpleAdapter simpleAdapter         = null;
    private LinearLayout topView                = null;
    private UserModel userModel                 = null;
    private Map<String,String> userInfo         = null;
    private TextView userNameView               = null;
    private List<Map<String, Object>>  listData = null;
    private Auth auth                           = null;
    private ImageView userIconView              = null;

    private String [] navigationTitles          = new String[]{
            "消息",
            "收藏",
            "我的评论",
            "风水咨询",
            "设置"
    };
    private int [] navigationImages             = new int[]{
            R.drawable.mine_xiaoxi,
            R.drawable.mine_shoucang,
            R.drawable.mine_pinglun,
            R.drawable.mine_zixun,
            R.drawable.mine_shezhi
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_myhome,container,false);
        initData();
        initView();
        return mainView;
    }

    private void initData() {
        listData = new ArrayList<>();
        for(int i = 0 ; i < navigationTitles.length ; i ++){
            Map<String,Object> map = new HashMap<>();
            map.put("item_myhome_activity_listview_icon",navigationImages[i]);
            map.put("item_myhome_activity_listview_title",navigationTitles[i]);
            listData.add(map);
        }
        simpleAdapter = new SimpleAdapter(
                getActivity(),
                listData,
                R.layout.item_myhome_activity_listview,
                new String[]{"item_myhome_activity_listview_icon","item_myhome_activity_listview_title"},
                new int[]{R.id.item_myhome_activity_listview_icon,R.id.item_myhome_activity_listview_title}
                );

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!Auth.isLogin(getActivity())){
            clearUserInfo();
        }else{
            setUserInfo();
        }
    }

    private void initView() {
        navigationList  = (ListView) mainView.findViewById(R.id.activity_myhome_navigation_list);
        topView         = (LinearLayout) mainView.findViewById(R.id.fragment_myhome_topbar);
        userNameView    = (TextView) mainView.findViewById(R.id.fragment_myhome_username);
        userIconView    = (ImageView) mainView.findViewById(R.id.fragment_myhome_usericon);

        topView.setOnClickListener(this);

        navigationList.setAdapter(simpleAdapter);

        auth = Auth.getInstance();
        userInfo = auth.login(getActivity());

        if(userInfo!=null){
            setUserInfo();
        }

        navigationList.setOnItemClickListener(new NavigationOnItemClickListener());

    }

    public Map<String, String> getUserInfo() {
        if(userInfo==null){
            userInfo = Auth.getInstance().login(getActivity());
        }
        return userInfo;
    }

    /**
     * 加载用户信息
     */
    private void setUserInfo() {
        if (getUserInfo()==null){
            return;
        }
        try {
            userNameView.setText(userInfo.get(UserModel.USER_NAME));
            setUserIcon(userInfo.get(UserModel.USER_ICON));
        }catch (Exception e){
            Helper.T(getActivity(),"设置用户信息失败" + getUserInfo().get(UserModel.USER_NAME) +  getUserInfo().get(UserModel.USER_ICON));
        }
    }

    private void clearUserInfo(){
        try {
            userNameView.setText("登录");
            userIconView.setImageResource(R.drawable.mine_head);
        }catch (Exception e){
        Helper.T(getActivity(),"清除用户信息失败");
    }
    }

    @Override
    public void onClick(View view) {
        if(!Auth.isLogin(getActivity())){
            auth.startLogin(getActivity(),new MyHomeOnAuthCallbackListener());
        }else{

            Intent intent = new Intent(getActivity(), ShowListActivity.class);
            intent.putExtra(UserModel.USER_PHONE, UserModel.getUser(getActivity()).get(UserModel.USER_PHONE));
            intent.putExtra(UserModel.USER_PASS, UserModel.getUser(getActivity()).get(UserModel.USER_PASS));
            intent.putExtra("ITEM_POSITION",300);
            getActivity().startActivity(intent);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        auth.onFragmentResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyHomeOnAuthCallbackListener implements Auth.OnAuthCallbackListener{

        @Override
        public void onSuccess(Intent data) {
           // userModel = new UserModel(data.getStringExtra("USER_INFO").toString());
           // userInfo =  userModel.getUser();
            auth = Auth.getInstance();
            userInfo = auth.login(getActivity());
            if(userInfo==null){
                return;
            }else{
                setUserInfo();
            }
        }

        @Override
        public void onError(Intent data) {

        }

        @Override
        public void onCancel(Intent data) {

        }
    }

    /**
     * 设置用户中心用户头像
     * @param iconPath
     */
    public void setUserIcon(String iconPath){
        if(iconPath.length()>10){
            iconPath=iconPath.replace("www.gzbjwb.cn","wanbao.7va.cn");
            Glide.with(getActivity())
                    .load(iconPath)
                    .placeholder(R.drawable.mine_head)
                    .into(userIconView);
        }else{
            userIconView.setImageResource(R.drawable.mine_head);
        }
    }

    private class NavigationOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(Auth.isLogin(getActivity())){
                    ShowListActivity.startHelper(
                            getActivity(),
                            i,
                            UserModel.getUser(getActivity()).get(UserModel.USER_PHONE),
                            UserModel.getUser(getActivity()).get(UserModel.USER_PASS));

            }else{
                auth.startLogin(getActivity(),new MyHomeOnAuthCallbackListener());
            }
        }
    }

}
