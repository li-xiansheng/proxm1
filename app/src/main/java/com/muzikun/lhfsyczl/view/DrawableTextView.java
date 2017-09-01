package com.muzikun.lhfsyczl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.muzikun.lhfsyczl.R;


/**
 * 可设置大小的textView
 */

public class DrawableTextView extends AppCompatTextView {
    boolean isChangeColor = true;
    boolean isChangeColorMatrix = true;
    Drawable textDrawable = null;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        /**
         * 取得自定义属性值
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        int drawableWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth, -1);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight, -1);
        isChangeColor = ta.getBoolean(R.styleable.DrawableTextView_isChangeColor, true);
        isChangeColorMatrix = ta.getBoolean(R.styleable.DrawableTextView_isChangeColorMatrix, true);

        /**
         * 取得TextView的Drawable(左上右下四个组成的数组值)
         */
        Drawable[] drawables = getCompoundDrawables();

        for (Drawable drawable : drawables) {
            if (drawable != null) {
                textDrawable = drawable;
            }
        }
        /**
         * 设置宽高
         */
        if (textDrawable != null && drawableWidth != -1 && drawableHeight != -1) {
            textDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        /**
         * 设置给TextView
         */
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        /**
         * 回收ta
         */
        ta.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                if (isChangeColor) {
                    this.setAlpha(1f);
                } else if (isChangeColorMatrix) {
                    changeLight(0);
                }
                break;
            case MotionEvent.ACTION_DOWN:

                if (isChangeColor) {
                    this.setAlpha(0.5f);
                } else if (isChangeColorMatrix) {
                    changeLight(-80);
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                    changeLight(-80);
//                    this.setAlpha(1f);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (isChangeColor) {
                    this.setAlpha(1f);
                } else if (isChangeColorMatrix) {
                    changeLight(0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }

    /**
     * 通过矩证变换 设置图标覆盖色彩
     *
     * @param brightness
     */
    private void changeLight(int brightness) {//ImageView imageview,
        ColorMatrix matrix = new ColorMatrix();

        matrix.set(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0});
        if (textDrawable != null)
            textDrawable.setColorFilter(new ColorMatrixColorFilter(matrix));
    }
}