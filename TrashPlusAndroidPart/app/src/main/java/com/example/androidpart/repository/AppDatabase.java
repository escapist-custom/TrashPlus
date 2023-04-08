package com.example.androidpart.repository;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RenameColumn;
import androidx.room.RoomDatabase;

import com.example.androidpart.domain.User;

@Database(entities = {User.class},
        version = TrashPlusContract.TrashEntry.DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrashPlusDao trashPlusDao();

}
