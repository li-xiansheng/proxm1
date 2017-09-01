package com.muzikun.one.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by likun on 16/7/27.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class PagerListPopupWindowLinearLayout extends LinearLayout {


    public PagerListPopupWindowLinearLayout(Context context) {
        super(context);
    }

    public PagerListPopupWindowLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerListPopupWindowLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);
    }
}
