package com.example.androidpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;

public class MainActivity extends AppCompatActivity {
    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
}