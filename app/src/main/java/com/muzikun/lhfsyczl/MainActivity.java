package com.muzikun.lhfsyczl;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.muzikun.lhfsyczl.activity.LoginActivity;
import com.muzikun.lhfsyczl.activity.MyForumActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.broadcast.LocalBroadcastManager;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.fragment.BetFragment;
import com.muzikun.lhfsyczl.fragment.ForumFragment;
import com.muzikun.lhfsyczl.fragment.HomeFragment;
import com.muzikun.lhfsyczl.fragment.MeFragment;
import com.muzikun.lhfsyczl.util.ExampleUtil;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ScreenUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * @date 创建时间：2017/5/25
 * @description 首页
 */

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    public BottomNavigationBar getmBottomNavigationBar() {
        return mBottomNavigationBar;
    }

    private Tencent mTencent;// 新建Tencent实例用于调用分享方法
    /**
     * 记录退出按键的最后时刻，初始为0
     */
    private long lastTime = 0;

    //消息数量提示--消息列表
    private BadgeItem badgeItem;

    //各个Fragment操作页面
    public Fragment currentFragment;
    private HomeFragment homeFragment;
    private BetFragment betFragment;
    private MeFragment meFragment;
    private ForumFragment forumFragment;
    //
    public boolean isLogin = false;
    private int currentPage = 0;

    //极光推送记录
    public static boolean isForeground = false;
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    //
    private final int personalPage = 2;

    private Dialog shareDialog;
    //分享传递参数
    private Bundle params;

    @Override
    public void getIntentData() {
        super.getIntentData();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());

        setTitle("六合彩票");

        menuStr = "分享";
        setTopRightButton(menuStr, new OnMenuClickListener() {
            @Override
            public void onClick() {
                if ("分享".equals(menuStr)) {
//                    ToastUtils.show("暂不开放");
                    initDialog();
                } else {
                    SPUtil spUtil = new SPUtil(mContext, Constants.USER_TABLE);
                    boolean islogin = spUtil.getBoolean(UserDB.isLogin, false);
                    if (islogin) {
                        jumpToActivity(MyForumActivity.class, false);
                    } else {
                        jumpToActivity(LoginActivity.class, false);
                    }

                }
            }
        });
        initTab();
        registerMessageReceiver();  // used for receive msg
        init();


    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    /**
     * 初始化tab
     */
    private void initTab() {

        mToolBar.setNavigationIcon(null);


        //
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        /*** the setting for BadgeItem ***/
        badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("")
                .setBackgroundColorResource(R.color.red)
                .setBorderWidth(0);

        /*** the setting for BottomNavigationBar ***/
        mBottomNavigationBar.
                addItem(new BottomNavigationItem(R.drawable.tab_home_checked, "六合彩票").
                        setInactiveIconResource(R.drawable.tab_home_normal).setActiveColorResource(R.color.whiteTab).
                        setInActiveColorResource(R.color.grayText))
                .addItem(new BottomNavigationItem(R.drawable.tab_bet_checked, "彩民投票").
                        setInactiveIconResource(R.drawable.tab_bet_normal).setActiveColorResource(R.color.whiteTab).
                        setInActiveColorResource(R.color.grayText))

                .addItem(new BottomNavigationItem(R.drawable.tab_forum_checked, "交流论坛").
                        setInactiveIconResource(R.drawable.tab_forum_normal).setActiveColorResource(R.color.whiteTab).
                        setInActiveColorResource(R.color.grayText))
                .addItem(new BottomNavigationItem(R.drawable.tab_me_checked, "个人中心").
                        setInactiveIconResource(R.drawable.tab_me_normal).setActiveColorResource(R.color.whiteTab).
                        setInActiveColorResource(R.color.grayText))//.setBadgeItem(badgeItem)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        mBottomNavigationBar.selectTab(currentPage);
    }

    @Override
    protected void initData() {
//        getMobData();
    }

//    /**
//     * 获取mob后台数据
//     */
//    private void getMobData() {
//        BmobQuery<WebFlag> query = new BmobQuery<>();
//        //执行查询，第一个参数为上下文，第二个参数为查找的回调
//        LoadingDialog.showDialog(mActivity);
//        query.findObjects(new FindListener<WebFlag>() {
//            @Override
//            public void done(List<WebFlag> list, BmobException e) {
//                LoadingDialog.closeDialog(mActivity);
//                if (e == null) {
//                    if (list != null && list.size() > 0) {
//                        WebFlag webFlag = list.get(0);
//                        if (webFlag.isFlag() == true) {
////                            Intent intent=new Intent(mContext,MainActivity.class);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
////                            startActivity(intent);
//                            //
//                            Bundle bundle = new Bundle();
//                            bundle.putString("name", "彩票");
//                            bundle.putString("url", webFlag.getUrl());
//                            jumpToActivity(WebPageActivity.class, bundle, true);
//                        }
//                        LogUtils.i(TAG, "查询成功：" + webFlag.getUrl());
//                    }
//                } else {
//                    LogUtils.i(TAG, "查询失败：" + e.getMessage());
//                }
//            }
//        });
//
//
//    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //处理回退键，
            long curTime = System.currentTimeMillis();
            if (curTime - lastTime >= 2000) {
                lastTime = curTime;
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                return true;
            }
            this.finish();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onTabSelected(int position) {


        switch (position) {
            case 0:

                this.getWindow().getDecorView().setFitsSystemWindows(true);
                showToolbar();
                setTitle("六合彩票");
                menuStr = "分享";
                menuResId = 0;
                invalidateOptionsMenu();

                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                jumpFragment(currentFragment, homeFragment, R.id.sub_content, "六合彩票");
                currentFragment = homeFragment;
                break;

            case 1:
                this.getWindow().getDecorView().setFitsSystemWindows(true);
                showToolbar();
                setTitle("彩民投票");
                menuStr = "";
                menuResId = 0;
                invalidateOptionsMenu();

                if (betFragment == null) {
                    betFragment = BetFragment.newInstance();
                }
                jumpFragment(currentFragment, betFragment, R.id.sub_content, "彩民投票");
                currentFragment = betFragment;
                break;

            case 2:
                this.getWindow().getDecorView().setFitsSystemWindows(true);
                showToolbar();
                setTitle("交流论坛");
                menuStr = "";
                menuResId = R.drawable.icon_msg_lingsheng3;
                invalidateOptionsMenu();
                if (forumFragment == null) {
                    forumFragment = ForumFragment.newInstance();
                }
                jumpFragment(currentFragment, forumFragment, R.id.sub_content, "交流论坛");
                currentFragment = forumFragment;
                break;
            case 3:
                this.getWindow().getDecorView().setFitsSystemWindows(false);
                hideToolbar();

                if (meFragment == null) {
                    meFragment = MeFragment.newInstance();
                }
                jumpFragment(currentFragment, meFragment, R.id.sub_content, "个人中心");
                currentFragment = meFragment;
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {
        LogUtils.i(TAG, "onTabUnselected");
    }


    /**
     * 初始化dialog
     */
    private void initDialog() {


        if (shareDialog == null) {
            shareDialog = new Dialog(this, R.style.time_dialog);
            shareDialog.setCancelable(true);
            shareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_share, null);
            shareDialog.setContentView(view);
            shareDialog.show();
            Window window = shareDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.AnimationFadeButtom); //设置窗口弹出动画
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(this).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
            //
            SelectedLayerTextView tvWeixin = (SelectedLayerTextView) view.findViewById(R.id.tvWeixin);
            SelectedLayerTextView tvWeixinFriend = (SelectedLayerTextView) view.findViewById(R.id.tvWeixinFriend);
            SelectedLayerTextView tvQQ = (SelectedLayerTextView) view.findViewById(R.id.tvQQ);
            SelectedLayerTextView tvQQspace = (SelectedLayerTextView) view.findViewById(R.id.tvQQspace);
            SelectedLayerTextView tvCancel = (SelectedLayerTextView) view.findViewById(R.id.tvCancel);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tvWeixin:
                            sendWebInfoToWeiXin(true);
                            closeShareDialog();
                            break;
                        case R.id.tvWeixinFriend:
                            sendWebInfoToWeiXin(false);
                            closeShareDialog();
                            break;
                        case R.id.tvQQ:
                            if (mTencent == null) {
                                mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());
                            }
                            shareQQ();
                            closeShareDialog();
                            break;
                        case R.id.tvQQspace:
                            if (mTencent == null) {
                                mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());
                            }
                            shareQQSpace();
                            closeShareDialog();
                            break;
                        case R.id.tvCancel:
                            closeShareDialog();
                            break;
                    }
                }
            };

            tvWeixin.setOnClickListener(onClickListener);
            tvWeixinFriend.setOnClickListener(onClickListener);
            tvQQ.setOnClickListener(onClickListener);
            tvQQspace.setOnClickListener(onClickListener);
            tvCancel.setOnClickListener(onClickListener);
        } else {
            shareDialog.show();
        }


    }


    private void closeShareDialog(){
        if (shareDialog!=null){
            shareDialog.dismiss();
            shareDialog = null;
        }
    }

    /**
     *
     */
    private void shareQQ() {

        params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "六合分享");// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, Constants.share_description);// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://a.app.qq.com/o/simple.jsp?pkgname=com.muzikun.lhfsyczl");//运用宝微下载
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://ad.076668.com/logo.png");// 网络图片地址　

// 　
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "六合风水预测资料");// 应用名称
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, Constants.share_description);
        //分享操作要在主线程中完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(mActivity, params, new MyIUiListener());
            }
        });
    }

    /**
     *
     */
    private void shareQQSpace() {

        params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "六合分享");// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, Constants.share_description);// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://a.app.qq.com/o/simple.jsp?pkgname=com.muzikun.lhfsyczl");//运用宝微下载
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://ad.076668.com/logo.png");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        // 分享操作要在主线程中完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQzone(mActivity, params, new MyIUiListener());
            }
        });

    }

    /**
     * 发送web网页信息到微信
     *
     * @param flag
     */
    private void sendWebInfoToWeiXin(boolean flag) {
        // IWXAPI 是第三方app和微信通信的openapi接口
        IWXAPI api;


        //实例化
        api = WXAPIFactory.createWXAPI(this, Constants.weiXinAPP_ID);
        api.registerApp(Constants.weiXinAPP_ID);

        WXWebpageObject webpage = new WXWebpageObject();
        if (flag) {//分享到微信好友
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.muzikun.lhfsyczl";//运用宝微下载
        } else {//分享到朋友圈，此处用相同的网页，也可随需求改为不同的网页，
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.muzikun.lhfsyczl";//运用宝微下载
        }

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "六合分享";
        msg.description = Constants.share_description;
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }


    @Override
    public void onTabReselected(int position) {
        LogUtils.i(TAG, "onTabReselected");

    }

    //极光相关

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        LogUtils.i(TAG, "极光推送消息" + msg);
    }


    @Override
    protected void onPause() {
        isForeground = false;//推送判断是否在前台
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    /**
     * 功能描述：分享回调
     */

    class MyIUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            // 操作成功
            ToastUtils.show("分享成功");
            if (shareDialog != null) {
                shareDialog.dismiss();
                shareDialog = null;
            }

        }

        @Override
        public void onError(UiError uiError) {
            // 分享异常
            ToastUtils.show("分享失败");
            shareDialog.dismiss();
            shareDialog = null;
            if (shareDialog != null) {
                shareDialog.dismiss();
                shareDialog = null;
            }
        }

        @Override
        public void onCancel() {
            // 分享取消
            ToastUtils.show("取消取消");
            shareDialog.dismiss();
            shareDialog = null;
            if (shareDialog != null) {
                shareDialog.dismiss();
                shareDialog = null;
            }
        }
    }

}
