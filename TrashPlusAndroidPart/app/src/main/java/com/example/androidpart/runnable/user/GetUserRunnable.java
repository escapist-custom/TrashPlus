package com.example.androidpart.runnable.user;

import com.example.androidpart.domain.DataTransfer;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

public class GetUserRunnable implements Runnable {
    private static UserTrashPlusDao dao;

    public GetUserRunnable(AppDatabase db) {
        dao = db.trashPlusDaoUser();
    }
    @Override
    public void run() {
        User user = dao.getUser();
        DataTransfer dataTransfer = new DataTransfer();
        dataTransfer.SetUser(user);
    }
}
