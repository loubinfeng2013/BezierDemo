package com.loubinfeng.www.bezierdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 画图板，通过贝塞尔曲线来处理点与点之间的平滑效果
 *
 * Created by loubinfeng on 2017/3/27.
 */

public class DrawPadView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mX;
    private float mY;

    public DrawPadView(Context context) {
        this(context,null);
    }

    public DrawPadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX,mY);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                //在这里，通过贝塞尔曲线处理点与点之间圆滑效果,将每一次细微滑动，当做绘制贝尔曲线
                float halfX = (x + mX)/2;
                float halfY = (y + mY)/2;
                mPath.quadTo(mX,mY,halfX,halfY);
                mX = x;
                mY = y;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
