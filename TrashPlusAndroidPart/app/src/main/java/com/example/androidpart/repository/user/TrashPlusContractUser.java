package com.example.androidpart.repository.user;

import android.provider.BaseColumns;

public class TrashPlusContractUser {


    private TrashPlusContractUser() {}

    public static class UserEntry implements BaseColumns {

        public static final String DATABASE_NAME = "AndroidTrashPlus.db";

        public static final int DATABASE_VERSION = 8;
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NICK_NAME = "nick_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_CONTROL_SUM = "control_sum";

    }

}
