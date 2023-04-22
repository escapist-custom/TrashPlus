package com.example.androidpart.runnable;

import android.content.Context;

import androidx.room.Room;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;
import com.example.androidpart.repository.UserTrashPlusDao;

public class InsertRunnable implements Runnable {
    private final User inputUser;
    private final UserTrashPlusDao userDao;

    public InsertRunnable(User inputUser, Context context, AppDatabase db) {
        this.inputUser = inputUser;
        userDao = db.trashPlusDao();
    }

    @Override
    public void run() {
        if (inputUser != null) userDao.insert(inputUser);
    }
}
