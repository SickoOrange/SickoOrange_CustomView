package com.example.orange.sickoorange_customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 由于画笔是全局变量 所有绘制图案都共享一个画笔
 * 所以在每次绘制新图案的时候都需要重新设置画笔的属性
 * 可以为每一个绘制图案设置一个新的画笔 实现解耦
 * Created by Orange on 23.09.2016.
 */
public class LoadingView extends View {

    private Paint mPaintOne;
    private Paint mPaintTwo;
    private Path path;
    private int mWidth;
    private int mHeight;

    //定义旋转的小圆球的默认半径
    private int mCircleRadius = 10;

    private float mDegree;
    private float mOffset;
    //偏移百分比
    private int mCirclePositiveShrinkPercent;
    private int mCircleNegativePercent;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画板
        init();
    }

    private void init() {
        mPaintOne = new Paint();
        mPaintOne.setColor(Color.RED);
        mPaintOne.setAntiAlias(true);

        mPaintTwo = new Paint();
        mPaintTwo.setColor(Color.RED);
        mPaintTwo.setAntiAlias(true);
        path = new Path();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 70;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 70;
        }
        System.out.println("measure" + mWidth + " " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDegree += 2f;
        if (mDegree == 360f) {
            mDegree = 0;
        }
        //旋转画布 造成旋转的效果
        canvas.rotate(mDegree, mWidth / 2, mHeight / 2);
        //两个小圆点互相靠近的距离 不断变化
        if (mCirclePositiveShrinkPercent < 100) {
            mOffset = (mHeight / 2 - mCircleRadius) * (mCirclePositiveShrinkPercent / 100f);
            //绘制下降小红点
             canvas.drawCircle(mWidth / 2, mCircleRadius + mOffset, mCircleRadius, mPaintOne);
            //绘制上升小蓝点
            canvas.drawCircle(mWidth / 2, mHeight - mCircleRadius - mOffset, mCircleRadius, mPaintTwo);
            //改变偏移百分比
            mCirclePositiveShrinkPercent += 1f;
        } else {
            if (mCircleNegativePercent < 100) {
                mOffset = (mHeight / 2 - mCircleRadius) * (mCircleNegativePercent / 100f);
                //绘制上升小红点
                 canvas.drawCircle(mWidth / 2, mHeight / 2 - mOffset, mCircleRadius, mPaintOne);
                //绘制下降小圆点
                canvas.drawCircle(mWidth / 2, mHeight / 2 + mOffset, mCircleRadius, mPaintTwo);
                //改变偏移百分比
                mCircleNegativePercent += 1f;
            } else {
                //实现在边缘处平滑的滑动
                canvas.drawCircle(mWidth / 2, mCircleRadius, mCircleRadius, mPaintOne);
                canvas.drawCircle(mWidth / 2, mHeight - mCircleRadius, mCircleRadius, mPaintTwo);
                //重置偏移变量 循环
                mCirclePositiveShrinkPercent = 0;
                mCircleNegativePercent = 0;
            }
        }

        //刷新 重绘
        invalidate();
        super.onDraw(canvas);
    }
}
