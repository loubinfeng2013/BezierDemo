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
 * Created by test on 2017/3/27.
 */

public class Bezier2View extends View {

    //起始点
    private float mStartPointX;
    private float mStartPointY;
    //结束点
    private float mEndPointX;
    private float mEndPointY;
    //控制点
    private float mFlagPointX;
    private float mFlagPointY;
    //连线画笔
    private Paint mLinePaint;
    //点画笔
    private Paint mPointPaint;
    //曲线画笔
    private Paint mBezierPaint;
    //曲线路径
    private Path mBezierPath;


    public Bezier2View(Context context) {
        this(context, null);
    }

    public Bezier2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(4);
        mLinePaint.setColor(Color.GREEN);
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.RED);
        mPointPaint.setStyle(Paint.Style.FILL);
        mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setStrokeWidth(2);
        mBezierPaint.setColor(Color.BLACK);
        mBezierPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2;
        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2;
        mFlagPointX = w / 2;
        mFlagPointY = h / 2 - 300;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画点
        canvas.drawPoint(mStartPointX, mStartPointY, mPointPaint);
        canvas.drawPoint(mEndPointX, mEndPointY, mPointPaint);
        canvas.drawPoint(mFlagPointX, mFlagPointY, mPointPaint);
        //画连线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointX, mFlagPointY, mLinePaint);
        canvas.drawLine(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY, mLinePaint);
        //画Bezier曲线
        mBezierPath.reset();
        mBezierPath.moveTo(mStartPointX,mStartPointY);
        mBezierPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mBezierPath,mBezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            mFlagPointX = event.getX();
            mFlagPointY = event.getY();
            invalidate();
        }
        return true;
    }
}
