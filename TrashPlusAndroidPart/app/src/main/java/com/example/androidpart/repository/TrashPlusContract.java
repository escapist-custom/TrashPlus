package com.example.androidpart.repository;

import android.provider.BaseColumns;

public class TrashPlusContract {


    private TrashPlusContract() {}

    public static class TrashEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String DATABASE_NAME = "AndroidTrashPlus.db";

        public static final int DATABASE_VERSION = 1;
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NICK_NAME = "nick_name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_BIRTH_DATE = "birth_date";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TrashPlusContract.TrashEntry.TABLE_NAME + ";";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TrashPlusContract.TrashEntry.TABLE_NAME + " (" +
                        TrashPlusContract.TrashEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        TrashPlusContract.TrashEntry.COLUMN_NICK_NAME + " TEXT NOT NULL, " +
                        TrashPlusContract.TrashEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                        TrashPlusContract.TrashEntry.COLUMN_BIRTH_DATE + " DATE NOT NULL, " +
                        TrashPlusContract.TrashEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                        TrashPlusContract.TrashEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";

    }

}
