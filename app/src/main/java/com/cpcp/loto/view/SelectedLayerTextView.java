package com.cpcp.loto.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * TextView，简单啊的TextView，用于点击时需要产生点击效果， 将TextView透明80%
 */

public class SelectedLayerTextView extends AppCompatTextView {
    public SelectedLayerTextView(Context context) {
        super(context);
    }

    public SelectedLayerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectedLayerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.hasOnClickListeners()){
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    this.setAlpha(1f);
                    break;
                case MotionEvent.ACTION_DOWN:
                    this.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    this.setAlpha(1f);
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        }else{
            return super.onTouchEvent(event);
        }
    }

}
