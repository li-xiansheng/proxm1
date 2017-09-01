package com.muzikun.lhfsyczl.activity;

import android.text.TextUtils;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.fragment.ForumFragment;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

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
