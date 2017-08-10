package com.cpcp.loto.view;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>
 * ImageView，简单啊的ImageVIew，用于点击时需要产生点击效果， 将ImageView透明80%
 */

public class SelectedMatrixLayerImageView extends AppCompatImageView {
    public SelectedMatrixLayerImageView(Context context) {
        super(context);
    }

    public SelectedMatrixLayerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectedMatrixLayerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.hasOnClickListeners()){
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    changeLight(0);
                    break;
                case MotionEvent.ACTION_DOWN:
                    changeLight(-80);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    changeLight(0);
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        }else{
            return super.onTouchEvent(event);
        }
    }


    /**
     * 通过矩证变换 设置图标覆盖色彩
     *
     * @param brightness
     */
    private void changeLight(int brightness) {//ImageView imageview,
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        this.setColorFilter(new ColorMatrixColorFilter(matrix));
    }
}
