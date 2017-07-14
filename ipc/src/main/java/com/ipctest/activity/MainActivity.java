package com.ipctest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ipctest.R;
import com.ipctest.bean.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookManagerActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presistFile();
    }

    private void presistFile() {
        new Thread(new Runnable() {


            @Override
            public void run() {
                User user = new User(23, "licht", true);

                File dir = new File("haha");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cacheFile = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "user.txt");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cacheFile));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "persist user: " + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (objectOutputStream != null)
                        try {
                            objectOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }).start();
    }
}
