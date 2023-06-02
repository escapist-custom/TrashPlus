package com.example.androidpart.runnable.user;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

public class InsertRunnableUser implements Runnable {
    private final User inputUser;
    private final UserTrashPlusDao userDao;

    public InsertRunnableUser(User inputUser, AppDatabase db) {
        this.inputUser = inputUser;
        userDao = db.trashPlusDaoUser();
    }

    @Override
    public void run() {
        userDao.deleteAll();
        userDao.insert(inputUser);
    }
}
