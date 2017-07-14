package com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.view.widget.ViewTest;

public class MainActivity extends AppCompatActivity {

    private ViewTest view_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_test = (ViewTest) findViewById(R.id.view_test);

//        view_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectAnimator.ofFloat(view_test, "translationX", 0, 100).setDuration(500).start();
//            }
//        });
    }
}
