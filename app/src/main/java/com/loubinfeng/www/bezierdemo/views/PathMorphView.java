package com.loubinfeng.www.bezierdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 路径变换
 * Created by loubinfeng on 2017/3/27.
 */

public class PathMorphView extends View implements View.OnClickListener{

    //起始点
    private float mStartPointX;
    private float mStartPointY;
    //结束点
    private float mEndPointX;
    private float mEndPointY;
    //控制点
    private float mFlagPoint1X;
    private float mFlagPoint1Y;
    private float mFlagPoint2X;
    private float mFlagPoint2Y;
    //连线画笔
    private Paint mLinePaint;
    //点画笔
    private Paint mPointPaint;
    //曲线画笔
    private Paint mBezierPaint;
    //曲线路径
    private Path mBezierPath;

    private ValueAnimator valueAnimator;

    public PathMorphView(Context context) {
        this(context, null);
    }

    public PathMorphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMorphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOnClickListener(this);
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
        mFlagPoint1X = w / 4;
        mFlagPoint1Y = h / 2;
        mFlagPoint2X = w * 3 / 4;
        mFlagPoint2Y = h / 2;
        valueAnimator = ValueAnimator.ofInt(h/2,h);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value =  (Integer) valueAnimator.getAnimatedValue();
                mFlagPoint1Y = value;
                mFlagPoint2Y = value;
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画点
        canvas.drawPoint(mStartPointX, mStartPointY, mPointPaint);
        canvas.drawPoint(mEndPointX, mEndPointY, mPointPaint);
        canvas.drawPoint(mFlagPoint1X, mFlagPoint1Y, mPointPaint);
        canvas.drawPoint(mFlagPoint2X, mFlagPoint2Y, mPointPaint);
        //画连线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPoint1X, mFlagPoint1Y, mLinePaint);
        canvas.drawLine(mFlagPoint1X, mFlagPoint1Y, mFlagPoint2X, mFlagPoint2Y, mLinePaint);
        canvas.drawLine(mFlagPoint2X, mFlagPoint2Y, mEndPointX, mEndPointY, mLinePaint);
        //画Bezier曲线
        mBezierPath.reset();
        mBezierPath.moveTo(mStartPointX,mStartPointY);
        mBezierPath.cubicTo(mFlagPoint1X,mFlagPoint1Y,mFlagPoint2X,mFlagPoint2Y,mEndPointX,mEndPointY);//三阶贝塞尔曲线api
        canvas.drawPath(mBezierPath,mBezierPaint);
    }

    @Override
    public void onClick(View view) {
        valueAnimator.start();
    }
}
