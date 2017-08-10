package com.cpcp.loto.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 自定义LinerLayout，简单啊的处理头部，等组合控件，点击时，产生点击效果， 将子控件全部透明80%
 */

public class SelectedLayerLinearLayout extends LinearLayout {

    private List<View> mView;

    public SelectedLayerLinearLayout(Context context) {
        super(context);
    }

    public SelectedLayerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectedLayerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.hasOnClickListeners()) {
            if (null == mView) {
                mView = new ArrayList<>();
                for (int i = 0; i < this.getChildCount(); i++) {
                    mView.add(this.getChildAt(i));
                }
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    for (View view : mView) {
                        view.setAlpha(1f);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    for (View view : mView) {
                        view.setAlpha(0.5f);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    // changeLight(view, 0);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    for (View view : mView) {
                        view.setAlpha(1f);
                    }
                    break;
                default:
                    break;

            }
            return super.onTouchEvent(event);

        } else {
            return super.onTouchEvent(event);
        }


    }


}
