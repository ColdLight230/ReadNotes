package com.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/15
 */

public class ViewGroupTest extends ViewGroup {

    public ViewGroupTest(Context context) {
        super(context);
    }

    public ViewGroupTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ev.getAction() != MotionEvent.ACTION_DOWN;
    }
}
