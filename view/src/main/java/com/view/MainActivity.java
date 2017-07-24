package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.view.widget.ViewTest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ViewTest view_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HorizontalScrollActivity.class));

            }
        });

//        view_test = (ViewTest) findViewById(R.id.view_test);

//        view_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectAnimator.ofFloat(view_test, "translationX", 0, 100).setDuration(500).start();
//            }
//        });

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            int width = view_test.getMeasuredWidth();
//            int height = view_test.getMeasuredHeight();
//            Log.d(TAG, "onWindowFocusChanged --- :" + width + ", " + height);
//        }
//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        view_test.post(new Runnable() {
//            @Override
//            public void run() {
//                int width = view_test.getMeasuredWidth();
//                int height = view_test.getMeasuredHeight();
//                Log.d(TAG, "view_test.post(new Runnable()) --- :" + width + ", " + height);
//            }
//        });
//
//        ViewTreeObserver observer = view_test.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                view_test.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                int width = view_test.getMeasuredWidth();
//                int height = view_test.getMeasuredHeight();
//                Log.d(TAG, "ViewTreeObserver --- :" + width + ", " + height);
//            }
//        });
//
//        int width = view_test.getMeasuredWidth();
//        int height = view_test.getMeasuredHeight();
//        Log.d(TAG, "onStart --- :" + width + ", " + height);
//
//    }
}
