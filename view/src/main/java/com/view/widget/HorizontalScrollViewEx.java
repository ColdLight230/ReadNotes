package com.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/19
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth;
        int measuredHeight;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            View childView = getChildAt(0);
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                measuredWidth = childView.getMeasuredWidth() * childCount;
                measuredHeight = childView.getMeasuredHeight();
                setMeasuredDimension(measuredWidth, measuredHeight);
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                measuredWidth = childView.getMeasuredWidth() * childCount;
                setMeasuredDimension(measuredWidth, heightSpecSize);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                measuredHeight = childView.getMeasuredHeight();
                setMeasuredDimension(widthSpecSize, measuredHeight);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.VISIBLE) {
                int measuredWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + measuredWidth, childView.getMeasuredHeight());
                childLeft += measuredWidth;
            }
        }
    }
}
