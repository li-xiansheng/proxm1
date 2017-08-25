package com.cpcp.loto.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cpcp.loto.R;

import java.util.List;

/**
 * 功能描述：自定义View实现圆点转盘
 */

public class CirclePointView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int radius;
    private float centerXY;
    private List<Integer> imgResList;
    //出师为-1，未选中
    private int currentIndex = -1;

    public List<Integer> getImgResList() {
        return imgResList;
    }

    public void setImgResList(List<Integer> imgResList) {
        this.imgResList = imgResList;
        invalidate();
    }

    public CirclePointView(Context context) {
        super(context);
        init(context);
    }

    public CirclePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CirclePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = this.getMeasuredWidth();
        mHeight = this.getMeasuredHeight();
        centerXY = (float) (mWidth / 2.0);
    }

    private void init(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bitmapRadius = 0;
        if (imgResList != null && imgResList.size() > 1) {
            for (int i = 0; i < imgResList.size(); i++) {


                int imgRes = imgResList.get(i);
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imgRes);
                if (bitmapRadius == 0) {
                    bitmapRadius = bitmap.getWidth() / 2;
                    radius = (int) (centerXY - bitmapRadius);
                }

//                double drawX = mWidth/2+getRoundX(mWidth - 2 * left, i + 1, imgResList.size(), 0);
//                double drawY = mWidth/2+getRoundY(mWidth - 2 * left, i + 1, imgResList.size(), 0);
                if (i == currentIndex) {
                    Bitmap bitmapCircle = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_luck_circle);
                    double drawXCircle = centerXY - bitmapCircle.getWidth() / 2 + radius * Math.cos(360 / imgResList.size() * i * Math.PI / 180);
                    double drawYCircle = centerXY - bitmapCircle.getWidth() / 2 + radius * Math.sin(360 / imgResList.size() * i * Math.PI / 180);

                    canvas.drawBitmap(bitmapCircle, (int) drawXCircle, (int) drawYCircle,null);

                }


                double drawX = centerXY - bitmapRadius + radius * Math.cos(360 / imgResList.size() * i * Math.PI / 180);
                double drawY = centerXY - bitmapRadius + radius * Math.sin(360 / imgResList.size() * i * Math.PI / 180);
                canvas.drawBitmap(bitmap, (int) drawX, (int) drawY, mPaint);


            }
        } else {

        }

    }

    /**
     * 设置当前选中的角标
     *
     * @param index
     */
     public  void setCurrentSelect(int index) {
        currentIndex = index;
        invalidate();
    }

    ;

//    /**
//     * 计算圆形等分扇形的点Y坐标
//     *
//     * @param r            圆形直径
//     * @param i            第几个等分扇形
//     * @param n            等分扇形个数
//     * @param offset_angle 与X轴偏移角度
//     * @return Y坐标
//     */
//    private double getRoundY(float r, int i, int n, float offset_angle) {
//        return r * Math.sin(i * 2 * Math.PI / n + Math.PI / 180
//                * offset_angle);
//    }
//
//    /**
//     * 计算圆形等分扇形的点X坐标
//     *
//     * @param r            圆形直径
//     * @param i            第几个等分扇形
//     * @param n            等分扇形个数
//     * @param offset_angle 与X轴偏移角度
//     * @return x坐标
//     */
//    private double getRoundX(float r, int i, int n, float offset_angle) {
//        return r * Math.cos(i * 2 * Math.PI / n + Math.PI / 180
//                * offset_angle);
//    }

}
