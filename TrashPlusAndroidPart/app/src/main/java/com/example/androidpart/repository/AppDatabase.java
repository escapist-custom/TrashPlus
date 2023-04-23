package com.example.androidpart.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.dao.UserTrashPlusDao;

@Database(entities = {User.class},
        version = TrashPlusContract.TrashEntry.DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserTrashPlusDao trashPlusDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, TrashPlusContract.TrashEntry.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
