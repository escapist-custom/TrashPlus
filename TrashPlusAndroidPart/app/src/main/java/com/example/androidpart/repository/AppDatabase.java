package com.example.androidpart.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidpart.domain.User;

@Database(entities = {User.class}, version = TrashPlusContract.TrashEntry.DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrashPlusDao trashPlusDao();

}
