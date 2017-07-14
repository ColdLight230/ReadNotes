package com.ipctest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ipctest.constant.Constant;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/11
 */

public class MessengerService extends Service {
    private final static String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    Log.d(TAG, "msg from client" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message mess = Message.obtain(null, Constant.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","已经收到消息，待会回复");
                    mess.setData(bundle);
                    try {
                        client.send(mess);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
