package com.example.androidpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.androidpart.databinding.ActivityMainBinding;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase db;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                TrashPlusContract.TrashEntry.DATABASE_NAME).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}