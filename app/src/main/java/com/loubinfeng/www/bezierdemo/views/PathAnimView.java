package com.loubinfeng.www.bezierdemo.views;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.loubinfeng.www.bezierdemo.util.BezierUtil;

/**
 * Created by test on 2017/3/31.
 */

public class PathAnimView extends View implements View.OnClickListener{

    private float mStartPointX;
    private float mStartPointY;
    private float mEndPointX;
    private float mEndPointY;
    private float mFlagPointX;
    private float mFlagPointY;
    private float mMovePointX;
    private float mMovePointY;
    private Path mPath;
    private Paint mPointPaint;
    private Paint mLinePaint;
    private Paint mMovePointPaint;
    private ValueAnimator BezierPointAnimator;

    public PathAnimView(Context context) {
        this(context,null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.GREEN);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mMovePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMovePointPaint.setColor(Color.RED);
        mMovePointPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 3;
        mStartPointY = h / 3;
        mEndPointX = w * 2/ 3;
        mEndPointY = h * 2 /3;
        mFlagPointX =  w * 2/ 3;
        mFlagPointY = h /3;
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;
        BezierPointAnimator = ValueAnimator.ofObject(new BezierPointEvaluator(new PointF(mFlagPointX,mFlagPointY)),new PointF(mStartPointX,mStartPointY),new PointF(mEndPointX,mEndPointY));
        BezierPointAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        BezierPointAnimator.setDuration(3000);
        BezierPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF movePoint = (PointF)valueAnimator.getAnimatedValue();
                mMovePointX = movePoint.x;
                mMovePointY = movePoint.y;
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mStartPointX,mStartPointY,20,mPointPaint);
        canvas.drawCircle(mEndPointX,mEndPointY,20,mPointPaint);
        canvas.drawCircle(mMovePointX,mMovePointY,20,mMovePointPaint);
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);
        mPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mLinePaint);
    }

    @Override
    public void onClick(View view) {
        BezierPointAnimator.start();
    }

    class BezierPointEvaluator implements TypeEvaluator<PointF>{

        private PointF mFlagP;

        public BezierPointEvaluator(PointF point){
            mFlagP = point;
        }

        @Override
        public PointF evaluate(float v, PointF pointF, PointF t1) {
            return BezierUtil.CalculateBezierPointForQuadratic(v,pointF,mFlagP,t1);
        }
    }
}
