package com.example.androidpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpart.repository.AppDatabase;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(MainActivity.this);

        setContentView(R.layout.activity_main);
    }
}