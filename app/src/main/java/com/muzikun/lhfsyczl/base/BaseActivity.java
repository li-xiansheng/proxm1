package com.muzikun.lhfsyczl.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.uihelper.StatusBar;
import com.muzikun.lhfsyczl.util.ClickEventUtils;
import com.muzikun.lhfsyczl.util.KeyBoardUtils;
import com.muzikun.lhfsyczl.util.LogUtils;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * 功能描述：该运用Activity页面的基类页
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 页面直接引用的LOG标识
     */
    protected String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected BaseActivity mActivity;
    /**
     * 通用标题布局
     */
    public Toolbar mToolBar;
    /**
     * 自定义标题名称
     */
    public AppCompatTextView tvTitle;
    protected LinearLayout mDecorView = null;//根布局
    private FrameLayout mContentView = null;//activity内容布局

    /**
     * 网络请求时，发生退出，跳转时，用于取消网络请求
     */
    public Subscription mSubscription;
    /**
     * 是否全屏，置于super.initBase(savedInstanceState);之前
     */
    protected boolean isFullScreen = false;
    /**
     * 是否内容布局占用状态栏-true为不占用
     */
    protected boolean isFitsSystemWindows = true;
    /**
     * 是否添加ToolBar
     */
    protected boolean isShowToolBar = true;
    /**
     * 填充布局
     */
    private ViewStub viewStub;
    /**
     * 状态栏颜色
     */
    protected int statusBarColor;
    /**
     * 目录名称
     */
    public String menuStr;
    /**
     * 目录图片
     */
    public int menuResId;
    /**
     * 记录是点击时屏幕的Y轴起点
     */
    private float startY;

    protected InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(TAG,"基类--onCreate");
        statusBarColor = R.color.colorPrimaryOne;//初始化颜色
        initBase(savedInstanceState);
        if (mDecorView == null) {
            initDecorView();//初始化跟布局（添加toolbar，添加mContentview给子布局留空间）
        }
        //如果已经创建就先把内容清空，再添加
        if (mContentView != null) {
            mContentView.removeAllViews();//mContentview清空里面的view
        }
        getIntentData();
        setContentView(getLayoutResId());    //添加布局
        ButterKnife.bind(this);//注明使用UI注解


        initView();
        initListener();
        initData();


    }

    /**
     * 初始化view后，重写该方法，初始化控件事件
     */
    protected void initListener() {

    }


    /**
     * 初始化基类参数--复写该方法处理是否可预先
     * 设置是否全屏
     * 设置是否不加载toolbar
     */

    protected void initBase(Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        //是否全屏
        if (isFullScreen) {
            isShowToolBar = false;
            //全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            StatusBar.setBackgroundResource(this, statusBarColor);
        }


        //网络判断

        //
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    /**
     * @return 页面布局
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化UI所需参数
     */
    protected abstract void initView();

    /**
     * 获取Intent传递数据，无则不覆写该方法
     */
    public void getIntentData() {

    }

    /**
     * 初始化数据--构造数据--网络数据等
     */
    protected abstract void initData();


    /**
     * 根布局添加title布局
     */
    private void initDecorView() {
        //根容器
        mDecorView = new LinearLayout(this);
        mDecorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDecorView.setOrientation(LinearLayout.VERTICAL);
        if (isShowToolBar) {
            //Title
            addToolBar();

        }
        addOtherViewBeforeContent();
        //内容
        mContentView = new FrameLayout(this);

        mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,1));
        mDecorView.addView(mContentView);

        addOtherViewAfterContent();
    }

    /**
     * 子类继承后添加其他布局，在Title之后，content之前添加添加
     */
    protected void addOtherViewBeforeContent() {
    }
    /**
     * 子类继承后添加其他布局，content内容布局之后添加添加
     */
    protected void addOtherViewAfterContent() {
    }


    /**
     * 重写布局设置，加入title布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        getLayoutInflater().inflate(layoutResID, mContentView);
        if(isFitsSystemWindows){
            mDecorView.setFitsSystemWindows(true);//保证沉浸式窗体，状态栏和标题栏不重叠
        }else{
            mDecorView.setFitsSystemWindows(false);//内容布局顶出状态栏
        }

        super.setContentView(mDecorView);

    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Intent intent, boolean finish) {

        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 启动Activity，
     *
     * @param clazz  到指定页面
     * @param bundle 传递参数
     * @param flags  Intent.setFlag参数，设置为小于0，则不设置
     * @param finish
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (flags > 0) {
            intent.setFlags(flags);
        }

        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * Fragment之间的切换
     *
     * @param from 当前
     * @param to   目标
     * @param id   视图布局
     * @param tag  传递参数
     */
    public void jumpFragment(Fragment from, Fragment to, int id, String tag) {
        if (to == null || from == to) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.pull_in, R.anim.fade_out);
        if (from == null) {
            transaction.add(id, to, tag);
        } else {
            transaction.hide(from);
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(id, to, tag);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 用于需要动态隐藏toolbar
     */
    protected void hideToolbar() {
        if (mToolBar != null && mToolBar.getVisibility() != View.GONE) {
            mToolBar.setVisibility(View.GONE);
        }
    }

    /**
     * 用于需要动态隐藏toolbar
     */
    protected void showToolbar() {
        if (mToolBar != null && mToolBar.getVisibility() != View.VISIBLE) {
            mToolBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
//                LogUtils.i(TAG, "运行onKeyUp");
//                mSubscription.unsubscribe();
//                ToastUtils.show("取消请求");
////                if(viewStub!=null){
////                    //添加站位文件--用于无数据提示刷新-显示
////                    View view = getLayoutInflater().inflate(R.layout.fragment_contentview, null);
////                    viewStub = (ViewStub) view.findViewById(R.id.viewStub);
////                    mContentView.addView(view);
////                    viewStub.inflate();
////                }
//
//                return true;
//            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果软键盘弹出，则关闭，如果软键盘关闭，则关闭失败
        KeyBoardUtils.hideSoftInput(this);
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();//回退时，网络未取消，取消当前网络请求
        }
        super.onDestroy();
    }


    /**
     * 针对Android系统页面存在EditTextView是，判断是点击非EditText是否消失软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isTrue = super.dispatchTouchEvent(ev);//让系统的东西先执行,如果有

        //事件组合一直持续，为保证判定一次，故在MotionEvent.ACTION_UP下执行键盘事件
        View view = this.getCurrentFocus();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        if (view != null && ev.getAction() == MotionEvent.ACTION_UP) {//屏幕上有光标，并且点击区域为EditText
            if (ClickEventUtils.isClickEditText(view, ev) || Math.abs(ev.getY() - startY) > 10) {//点击的是EditText或者手指滑动一段距离都不隐藏
                // LogUtils.i(TAG, "屏幕上有光标-点击的是输入框--不隐藏");
            } else {
                //LogUtils.i(TAG, "屏幕上有光标-点击的不是输入框--隐藏输入框");
                KeyBoardUtils.hideSoftInput(this);
            }
        }
        return isTrue;
    }

    /**------------------------------------标题，菜单设置-------------------------------------------------*/
    /**
     * 实例化Toolbar，设置基本参数
     */
    protected void addToolBar() {
        View view = getLayoutInflater().inflate(R.layout.title_bar, mDecorView);
        mToolBar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolBar.setBackgroundResource(statusBarColor);
        tvTitle = (AppCompatTextView) view.findViewById(R.id.tv_title);
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationOnclick();
            }
        });
        //初始化设置 Toolbar
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    /**
     * 默认左边按钮返回事件为回退，重写该方法，重新定义操作
     */
    protected void navigationOnclick() {
        this.finish();

    }

    OnMenuClickListener onClickListenerToolbarRight;

    /**
     * 右菜单接口
     */
    public interface OnMenuClickListener {
        void onClick();
    }

    /**
     * 设置右菜单-文字
     *
     * @param menuStr
     * @param onClickListener
     */
    protected void setTopRightButton(String menuStr, OnMenuClickListener onClickListener) {
        this.onClickListenerToolbarRight = onClickListener;
        this.menuStr = menuStr;
    }

    /**
     * 设置右菜单-文字提示和图片
     *
     * @param menuStr
     * @param menuResId
     * @param onClickListener
     */
    protected void setTopRightButton(String menuStr, int menuResId, OnMenuClickListener onClickListener) {
        this.menuResId = menuResId;
        this.menuStr = menuStr;
        this.onClickListenerToolbarRight = onClickListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_right, menu);
        }
        return true;
    }

    /**
     * 菜单事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //        return super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            navigationOnclick();
        } else if (item.getItemId() == R.id.action_right) {
            onClickListenerToolbarRight.onClick();
        }

        return true; // true 告诉系统我们自己处理了点击事件

    }

    /**
     * 右边菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.action_right).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.action_right).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    /**----------------------------------------菜单结束--------------------------------------------*/
}
