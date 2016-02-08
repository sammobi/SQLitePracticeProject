package com.homestudy.sqlitepracticeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class SQLiteAdapter {

    private static final String DATABASE_NAME = "db_sqlitepracticeproject";
    private static final int DATABASE_VERSION = 1;

    /* database tables */
    private static final String TABLE_USER = "tbl_user";

	/* TABLE_USER_LIBRARY table fields */

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL_ID = "email_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";


    /* create table tbl_user_books_status script */
    private static final String SCRIPT_CREATE_TABLE_USER = "create table if not exists " + TABLE_USER
            + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_EMAIL_ID + " text, " + KEY_USERNAME
            + " text," + KEY_PASSWORD + " text ," + KEY_NAME + " text);";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public SQLiteAdapter(Context context) {
        this.context = context;
    }

    /**
     * Open database with read only permission.
     *
     * @return
     * @throws android.database.SQLException
     */
    private SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        Log.d(this.getClass().getName(), "openToRead");
        return this;
    }

    /**
     * Open database with read/write permission
     *
     * @return
     * @throws android.database.SQLException
     */
    private SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        Log.d(this.getClass().getName(), "openToWrite");
        return this;
    }

    /**
     * Close database
     */
    private void close() {
        sqLiteHelper.close();
        Log.d(this.getClass().getName(), "close");
    }

    /**
     * @return number of rows deleted
     */
    public int deleteUserTable() {
        openToWrite();
        int result = sqLiteDatabase.delete(TABLE_USER, null, null);
        close();
        Log.d(this.getClass().getName(), "All log files deleted successfully.");
        return result;
    }

    public long insertOrUpdateUser(String emailId, String userName, String name, String passWord) {
        if (null == emailId || null == userName || null == name || null == passWord) {
            return -1;
        }

        boolean isRowExists = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL_ID, emailId);
        contentValues.put(KEY_USERNAME, userName);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PASSWORD, passWord);


        openToRead();
        String Query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_EMAIL_ID + "=? AND " + KEY_USERNAME + "=? AND " + KEY_NAME + "=? AND " + KEY_PASSWORD + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(Query, new String[]{emailId, userName, name, passWord});

        if (cursor.moveToFirst()) {
            isRowExists = true;
        } else {
            isRowExists = false;
        }
        close();

        openToWrite();
        long result;
        if (!isRowExists)
            result = sqLiteDatabase.insert(TABLE_USER, null, contentValues);
        else
            result = sqLiteDatabase.update(TABLE_USER, contentValues, KEY_EMAIL_ID + "=? AND " + KEY_USERNAME + "=? AND " + KEY_NAME + "=? AND " + KEY_PASSWORD + "=?",
                    new String[]{emailId, userName, name, passWord});
        close();
        return result;
    }

    public Boolean isUserExist(String userName, String password) {
        if (null == userName || null == password) {
            return false;
        }

        Boolean userExist = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, userName);
        contentValues.put(KEY_PASSWORD, password);


        openToRead();
        String Query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(Query, new String[]{userName, password});

        if (cursor.moveToFirst()) {
            userExist = true;
        } else {
            userExist = false;
        }
        close();

        return userExist;
    }

    /**
     * Provides functionality for data definition
     */
    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, Environment.getExternalStorageDirectory() + "/com.homestudy.sqlitepracticeproject/" + name, null, version);
            // super(context, name, null, version);
        }

        /**
         * Handle on database create
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SCRIPT_CREATE_TABLE_USER);

        }

        /**
         * Handle on database update
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}