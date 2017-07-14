package com.ipctest.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ipctest.R;
import com.ipctest.bean.Book;
import com.ipctest.bean.User;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/12
 */

public class ProviderActivity extends AppCompatActivity {
    private final static String TAG = "ProviderActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse("content://com.ipctest.provider.book.provider/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 6);
        contentValues.put("name", "三体");
        getContentResolver().insert(bookUri, contentValues);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG, "book: " + book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.ipctest.provider.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.age = userCursor.getInt(0);
            user.name = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.d(TAG, "user: " + user.toString());
        }
        userCursor.close();
    }
}
