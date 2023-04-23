package com.example.androidpart.runnable;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.dao.UserTrashPlusDao;

public class InsertRunnable implements Runnable {
    private final User inputUser;
    private final UserTrashPlusDao userDao;

    public InsertRunnable(User inputUser, AppDatabase db) {
        this.inputUser = inputUser;
        userDao = db.trashPlusDao();
    }

    @Override
    public void run() {
        userDao.deleteAll();
        userDao.insert(inputUser);
    }
}
