package com.example.androidpart.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidpart.domain.User;

public class TrashPlusDbOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public TrashPlusDbOpenHelper(Context context) {
        super(context, TrashPlusContract.TrashEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrashPlusContract.TrashEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TrashPlusContract.TrashEntry.SQL_DELETE_ENTRIES);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrashPlusContract.TrashEntry.COLUMN_NICK_NAME, user.getNickName());
        values.put(TrashPlusContract.TrashEntry.COLUMN_ADDRESS, user.getAddress());
        values.put(TrashPlusContract.TrashEntry.COLUMN_BIRTH_DATE, user.getBirthDate());
        values.put(TrashPlusContract.TrashEntry.COLUMN_EMAIL, user.getEmail());
        values.put(TrashPlusContract.TrashEntry.COLUMN_PASSWORD, user.getPassword());

        long insert = db.insert(TrashPlusContract.TrashEntry.TABLE_NAME, null, values);

        return insert > 0;
    }
}
