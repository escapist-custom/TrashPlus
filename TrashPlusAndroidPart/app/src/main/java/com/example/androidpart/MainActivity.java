package com.example.androidpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.runnable.user.UserUpdateRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(MainActivity.this);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.getPrimaryNavigationFragment();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserUpdateRunnable userUpdateRunnable = new UserUpdateRunnable(db, fragment);
        executorService.execute(userUpdateRunnable);
    }
}