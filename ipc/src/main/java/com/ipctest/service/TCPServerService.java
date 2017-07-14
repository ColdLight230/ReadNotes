package com.ipctest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/12
 */

public class TCPServerService extends Service {

    private final static String TAG = "TCPServerService";

    private boolean isServiceDestoryed = false;

    private String[] mDefinedMessages = new String[]{
            "hello...",
            "what is your name",
            "today is good",
            "lalalalala"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        isServiceDestoryed = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            while (!isServiceDestoryed) {
                try {
                    final Socket accept = serverSocket.accept();
                    Log.d(TAG, "accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(accept);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //接受客户消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

        out.println("欢迎来到聊天室！");
        while (!isServiceDestoryed) {
            String line = in.readLine();
            Log.d(TAG, "msg from client: " + line);
            if (line == null) {
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            Log.d(TAG, "send : " + msg);
        }

        Log.d(TAG, "client quit");
        out.close();
        in.close();
        client.close();
    }
}
