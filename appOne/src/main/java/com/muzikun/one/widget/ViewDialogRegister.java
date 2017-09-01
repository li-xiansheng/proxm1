package com.muzikun.one.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.util.EventUtil;

/**
 * @author 冒国全
 *         创建时间：2015年9月11日 下午4:35:28
 *         已经设置dialog的监听事件
 *         <p>
 *         注册界面及其他界面的弹出显示内容
 *         </p>
 */
public class ViewDialogRegister extends Dialog {
    protected static final String TAG = "MyDialog";


    private ImageView dialogScaleIv;

    private TextView dialogScaleTv;

    private ImageView dialogSureIv;

    private RelativeLayout dialogRl;

    private TextView dialogTitleTv;

    private TextView dialogContentTv;

    /**
     * 要显示的结果字符串
     */
    private int type;
    private CharSequence titleTxt;
    private CharSequence contentTxt;
    private CharSequence scaleTxt;

    /**
     * 当前对话框是否已显示
     */
    public boolean isShow;

    private boolean isShowCertainButton;

    private boolean isShowCancelButton;


    private OnDialogClickListener mCancelClickListener;
    private OnDialogClickListener mConfirmClickListener;


    public ViewDialogRegister(Context context) {
        super(context);
    }

    public ViewDialogRegister(Context context, int theme) {
        super(context, theme);
    }

    public static interface OnDialogClickListener {
        public void onClick(ViewDialogRegister viewDialogRegister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "dialog onCreate!");

        setContentView(R.layout.dark_dialog_register);
        initView();

        setBackGroundType(type);
        setTitleText(titleTxt);
        setContentText(contentTxt);
        setScaleText(scaleTxt);

        EventUtil.setTouchState(dialogSureIv);

        dialogSureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {

        dialogScaleIv = (ImageView) findViewById(R.id.dialog_scale_iv);
        dialogScaleTv = (TextView) findViewById(R.id.dialog_scale_tv);
        dialogSureIv = (ImageView) findViewById(R.id.dialog_sure_iv);
        dialogRl = (RelativeLayout) findViewById(R.id.dialog_rl);
        dialogTitleTv = (TextView) findViewById(R.id.dialog_title_tv);
        dialogContentTv = (TextView) findViewById(R.id.dialog_content_tv);
    }


//    /**
//     * 让对话框显示要提示的内容
//     *
//     * @param checkResult 要显示的内容
//     */
//    public void showError(String checkResult) {
//        result = checkResult;
//        show();
//    }

    public ViewDialogRegister setBackGroundType(int type) {
        this.type = type;
        if (dialogRl != null && dialogScaleIv != null && dialogSureIv != null) {
            if (type == 1) {
                dialogRl.setBackgroundResource(R.drawable.honh);
                dialogScaleIv.setImageResource(R.drawable.jili);
                dialogSureIv.setImageResource(R.drawable.queding);
            } else if (type == 2) {
                dialogRl.setBackgroundResource(R.drawable.huang);
                dialogScaleIv.setImageResource(R.drawable.xiong);
                dialogSureIv.setImageResource(R.drawable.quedingd);
            } else {
                dialogRl.setBackgroundResource(R.drawable.red);
                dialogScaleIv.setImageResource(0);
                dialogSureIv.setImageResource(R.drawable.qued);
            }
        }

        return this;
    }

    public ViewDialogRegister setTitleText(CharSequence text) {
        titleTxt = text;
        if (dialogTitleTv != null && text != null) {
            dialogTitleTv.setText(titleTxt);
        }
        return this;
    }

    public ViewDialogRegister setContentText(CharSequence text) {
        contentTxt = text;
        if (dialogContentTv != null && text != null) {
            dialogContentTv.setText(contentTxt);
        }
        return this;
    }

    public ViewDialogRegister setScaleText(CharSequence text) {
        scaleTxt = text;
        if (dialogScaleTv != null && text != null) {
            dialogScaleTv.setText(text);
        }
        return this;
    }

    public ViewDialogRegister setCancelClickListener(OnDialogClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public ViewDialogRegister setCertainClickListener(OnDialogClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }


}