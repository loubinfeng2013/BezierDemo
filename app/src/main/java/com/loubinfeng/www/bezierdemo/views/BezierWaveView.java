package com.loubinfeng.www.bezierdemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.animation.ValueAnimator.INFINITE;

/**
 * Created by loubinfeng on 2017/3/30.
 */

public class BezierWaveView extends View implements ValueAnimator.AnimatorUpdateListener,View.OnClickListener{

    //用来填充波浪的画笔
    private Paint mPaint;
    //波浪曲线的路径
    private Path mPath;
    //控件的宽
    private int mViewWidth;
    //控件的高
    private int mViewHeight;
    //一个波浪的宽度,一起一伏为一个波浪
    private int mWaveWidth;
    //一共需要绘制4个波浪
    private int mWaveCount = 4;
    //绘制bezier曲线的控制点的高度
    private float mFlagHeight;
    //原点的纵向位置
    private float mStartY;
    //偏移量
    private int offset;
    //控制offset值动画
    private ValueAnimator mValueAnimator;

    public BezierWaveView(Context context) {
        this(context,null);
    }

    public BezierWaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOnClickListener(this);
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mWaveWidth = w / 2 ;
        mFlagHeight = h / 6;
        mStartY = h / 2;
        mValueAnimator = ValueAnimator.ofInt(-mWaveWidth,0);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(INFINITE);
        mValueAnimator.addUpdateListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null){
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveWidth,mStartY);
        for (int i = 0 ; i < mWaveCount ; i++){
            mPath.quadTo(-mWaveWidth+mWaveWidth/4+i*mWaveWidth+offset,mStartY + mFlagHeight,-mWaveWidth+mWaveWidth/2+i*mWaveWidth+offset,mStartY);
            mPath.quadTo(-mWaveWidth+mWaveWidth*3/4+i*mWaveWidth+offset,mStartY-mFlagHeight,-mWaveWidth+mWaveWidth+i*mWaveWidth+offset,mStartY);
        }
        mPath.lineTo(mViewWidth,mViewHeight);
        mPath.lineTo(0,mViewHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        offset = (Integer) valueAnimator.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onClick(View view) {
        mValueAnimator.start();
    }
}
