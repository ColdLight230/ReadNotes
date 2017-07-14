package com.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/14
 */

public class ViewTest extends View {

    int lastX;
    int lastY;

    private final static String TAG = "ViewTest";

    public ViewTest(Context context) {
        super(context);
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft() + offsetX, getTop() + offsetY,
                        getRight() + offsetX , getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
