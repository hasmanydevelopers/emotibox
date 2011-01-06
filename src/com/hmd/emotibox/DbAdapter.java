package com.hmd.emotibox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {

    public static final String KEY_COUNT = "count";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "EmotiboxDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_CREATE =
            "create table emotibox (_id integer primary key, "
                    + "count integer not null default 0);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "emotibox";
    private static final String RECORD_LIMIT = "5";
    private static final String ORDER_BY = "count DESC";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }
    
    private long createRecord(long rowId) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, rowId);
        initialValues.put(KEY_COUNT, 0);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    private Cursor fetchRecord(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_COUNT}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    public Cursor fetchMostUsed() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_COUNT}, null, null, null, null, ORDER_BY, RECORD_LIMIT);
    }
    
    public boolean updateRecord(long rowId) {
        Cursor mCursor = fetchRecord(rowId);
        int value = 0;
        int count = mCursor.getCount();
        
        if ((mCursor == null) || (count == 0)){
            createRecord(rowId);
        }
        else{
            value = mCursor.getInt(1);
        }
        value++;
        ContentValues args = new ContentValues();
        args.put(KEY_COUNT, value);
        
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
