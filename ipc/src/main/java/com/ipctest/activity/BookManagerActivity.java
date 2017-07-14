package com.ipctest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ipctest.IBookManager;
import com.ipctest.R;
import com.ipctest.bean.Book;
import com.ipctest.service.BookManagerService;

import java.util.List;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/13
 */

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "connected");
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = iBookManager.getBookList();
                Log.d(TAG, "booklist type: " + bookList.getClass().getCanonicalName());
                Log.d(TAG, "bookList :" + bookList.toString());
                iBookManager.addBook(new Book(333, "Html5"));
                Log.d(TAG, "bookList :" + iBookManager.getBookList().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "disconnected");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
