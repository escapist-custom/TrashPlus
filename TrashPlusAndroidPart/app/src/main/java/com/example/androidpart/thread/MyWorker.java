package com.example.androidpart.thread;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;
import com.example.androidpart.repository.TrashPlusDao;

public class MyWorker extends Worker {

    public static final String TAG = "MY_TAG";
    private AppDatabase db = Room.databaseBuilder(getApplicationContext(),
    AppDatabase.class, TrashPlusContract.TrashEntry.DATABASE_NAME).build();;
    private User userIn;
    private User userOut;
    private Data dataOut;
    private TrashPlusDao dao;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        synchronized (this) {
            dao = db.trashPlusDao();

            userIn = new User(
                    getInputData().getString("Nickname"),
                    getInputData().getString("Address"),
                    getInputData().getString("BirthDate"),
                    getInputData().getString("Email"),
                    getInputData().getString("Password")
            );

            dao.insert(userIn);
            userOut = dao.findByEmail(userIn.getEmail());

            dataOut = new Data.Builder()
                    .putString("NicknameOut", userOut.getNickName())
                    .putString("AddressOut", userOut.getAddress())
                    .putString("BirthDateOut", userOut.getBirthDate())
                    .putString("EmailOut", userOut.getEmail())
                    .putString("PasswordOut", userOut.getPassword())
                    .build();

        }
        return Worker.Result.success(dataOut);
    }
}
