package com.ipctest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ipctest.db.DbOpenHelper;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/12
 */

public class BookProvider extends ContentProvider {
    private final static String TAG = "BookProvider";
    public static final String AUTHORITY = "com.ipctest.provider.book.provider";

    public static final String BOOK_CONTENT_URI = "content://" + AUTHORITY + "/book";
    public static final String USER_CONTENT_URI = "content://" + AUTHORITY + "/user";

    public static final int BOOK_CONTENT_CODE = 0;
    public static final int USER_CONTENT_CODE = 1;

    private static final UriMatcher sURIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMATCHER.addURI(AUTHORITY, "book", BOOK_CONTENT_CODE);
        sURIMATCHER.addURI(AUTHORITY, "user", USER_CONTENT_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sURIMATCHER.match(uri)) {
            case BOOK_CONTENT_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_CONTENT_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate, current thread: " + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3, 'Android'); ");
        mDb.execSQL("insert into book values(4, 'iOS'); ");
        mDb.execSQL("insert into book values(5, 'Html5'); ");
        mDb.execSQL("insert into user values(1, 'licht', 1); ");
        mDb.execSQL("insert into user values(2, 'ivy', 0); ");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType, current thread: " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query, current thread: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri:" + uri);
        }

        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert, current thread: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri:" + uri);
        }
        mDb.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete, current thread: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri:" + uri);
        }
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update, current thread: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri:" + uri);
        }
        int count = mDb.update(tableName, values, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }
}
