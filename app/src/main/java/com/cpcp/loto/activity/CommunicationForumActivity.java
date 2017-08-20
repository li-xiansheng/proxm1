package com.cpcp.loto.activity;

import android.text.TextUtils;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.ForumFragment;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

/**
 * 功能描述：交流论坛
 */

public class CommunicationForumActivity extends BaseActivity {
    private ForumFragment forumFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_communication;


    }

    @Override
    protected void initView() {
        setTitle("交流论坛");
        setTopRightButton("铃声", R.drawable.icon_msg_lingsheng3, new OnMenuClickListener() {
            @Override
            public void onClick() {
                SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
                if (!isLogin) {
                    jumpToActivity(LoginActivity.class, false);
                    return;
                }
                String username = sp.getString(UserDB.TEL, "");
                if (TextUtils.isEmpty(username)) {
                    jumpToActivity(LoginActivity.class, false);
                    ToastUtils.show("登录过期，请重新登录");
                    return;
                }
                jumpToActivity(MyForumActivity.class, false);
            }
        });


        if (forumFragment == null) {
            forumFragment = ForumFragment.newInstance();
        }
        jumpFragment(null, forumFragment, R.id.sub_content, "交流论坛");


    }

    @Override
    protected void initData() {

    }
}
