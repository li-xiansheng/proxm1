package com.bigkoo.pickerview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.listener.OnDismissListener;

/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView {
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );

    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//附加View 的 根View

    private OnDismissListener onDismissListener;
    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;
    private boolean isShowing;
    private int gravity = Gravity.BOTTOM;

    private PopupWindow mPopupWindow;

    public BasePickerView(Context context){
        this.context = context;

        initViews();
        init();
        initEvents();
    }

    protected void initViews(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
        contentContainer.setLayoutParams(params);
    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }
    protected void initEvents() {
    }
    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
//        decorView.addView(view);
//        contentContainer.startAnimation(inAnim);
        mPopupWindow=  new PopupWindow(context);

        // when a touch even happens outside of the window
        // make the window go away
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable colorDrawable=new ColorDrawable(Color.argb(40, 0, 0, 0));
        //window.set
        mPopupWindow.setBackgroundDrawable(colorDrawable);
        mPopupWindow.setAnimationStyle(R.style.pickerTimeView);
        mPopupWindow.setContentView(rootView);
        mPopupWindow.showAtLocation(decorView,Gravity.BOTTOM,0,0);
    }
    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        isShowing = true;
        onAttached(rootView);
    }
    /**
     * 检测该View是不是已经添加到根视图
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null || isShowing;
    }

    public void dismiss() {
        if (dismissing) {
            return;
        }

        dismissing = true;

//        //消失动画
//        outAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                decorView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        dismissImmediately();
//                    }
//                });
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        contentContainer.startAnimation(outAnim);
        mPopupWindow.dismiss();
    }

    public void dismissImmediately() {
        //从activity根视图移除
        decorView.removeView(rootView);
        isShowing = false;
        dismissing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(BasePickerView.this);
        }

    }
    public Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public BasePickerView setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        }
        else{
            view.setOnTouchListener(null);
        }
        return this;
    }
    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id){
        return contentContainer.findViewById(id);
    }
}
