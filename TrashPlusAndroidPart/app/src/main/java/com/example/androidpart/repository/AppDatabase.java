package com.example.androidpart.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.TrashPlusContractUser;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

@Database(entities = {User.class, Product.class},
        version = TrashPlusContractUser.UserEntry.DATABASE_VERSION
    )
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public abstract UserTrashPlusDao trashPlusDaoUser();
    public abstract ProductTrashPlusDao trashPlusDaoProduct();
    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, TrashPlusContractUser.UserEntry.DATABASE_NAME)
                            .addMigrations(MIGRATION_2_3, MIGRATION_1_2, MIGRATION_3_4,
                                    MIGRATION_4_5)
                            .build();
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE products ("
                    + " 'name' TEXT, 'information' TEXT,  `id` INTEGER NOT NULL, " +
                    "'product_code' INTEGER NOT NULL, PRIMARY KEY('id'))"
            );
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE products ADD photo_link TEXT;");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE products ADD user_id INTEGER DEFAULT 0 NOT NULL;");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE products ADD cover_code TEXT;");
        }
    };

}
