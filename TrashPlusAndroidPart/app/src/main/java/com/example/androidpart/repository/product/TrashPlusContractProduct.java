package com.example.androidpart.repository.product;

import android.provider.BaseColumns;

public class TrashPlusContractProduct {

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRODUCT_CODE = "product_code";
        public static final String COLUMN_INFO = "information";
        public static final String COLUMN_PHOTO_LINK = "photo_link";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_COVER_CODE = "cover_code";
        public static final String COLUMN_JUST_ADDED = "flag_added";

    }


}
