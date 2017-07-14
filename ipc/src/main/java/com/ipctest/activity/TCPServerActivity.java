package com.ipctest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ipctest.R;
import com.ipctest.service.TCPServerService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/12
 */

public class TCPServerActivity extends AppCompatActivity {

    private final static String TAG = "TCPServerActivity";

    private final static int MESSAGE_RECEIVER_NEW_MSG = 0;
    private final static int MESSAGE_SOCKET_CONNECTED = 1;

    private Button btn_send;
    private EditText edit_text;
    private TextView tv_content;
    private Socket mClientSocket;
    private PrintWriter printWriter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SOCKET_CONNECTED:
                    btn_send.setEnabled(true);
                    break;
                case MESSAGE_RECEIVER_NEW_MSG:
                    tv_content.setText(tv_content.getText() + (String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_server);
        btn_send = (Button) findViewById(R.id.button_send);
        edit_text = (EditText) findViewById(R.id.edit_text);
        tv_content = (TextView) findViewById(R.id.content);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = edit_text.getText().toString().trim();
                if (!TextUtils.isEmpty(msg) && printWriter != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            printWriter.println(msg);
                        }
                    }).start();
                    edit_text.setText("");
                    String time = formaDateTime(System.currentTimeMillis());
                    String showedMsg = "self " + time + ":" + msg + "\n";
                    tv_content.setText(tv_content.getText() + showedMsg);
                }
            }
        });

        Intent intent = new Intent(this, TCPServerService.class);
        startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                connectionTcpServer();
            }
        }).start();
    }

    private void connectionTcpServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "connect success");
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                Log.d(TAG, "connect failed, retry...");
            }
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPServerActivity.this.isFinishing()) {
                String readLine = bufferedReader.readLine();
                Log.d(TAG, "receive :" + readLine);

                if (readLine != null) {
                    String time = formaDateTime(System.currentTimeMillis());
                    String showedStr = "server " + time + ":" + readLine + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVER_NEW_MSG, showedStr).sendToTarget();
                }
            }
            Log.d(TAG, "quit...");
            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formaDateTime(long l) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(l));
    }

}
