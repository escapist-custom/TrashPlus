package com.example.androidpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.user.TrashPlusContractUser;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase db;
    public static boolean PERMITTED;
    private static final int PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (allPermissionGranted()) {
            PERMITTED = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE
            );
        }
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                TrashPlusContractUser.TrashEntry.DATABASE_NAME).build();
    }

    private boolean allPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }
}