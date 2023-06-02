package com.example.androidpart.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.databinding.ScanningFragmentBinding;
import com.example.androidpart.domain.Product;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.example.androidpart.service.QRAnalyzer;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.Result;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ScanningFragment extends Fragment {

    private ScanningFragmentBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 10;
    private QRAnalyzer qrAnalyzer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ScanningFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        qrAnalyzer = new QRAnalyzer(getContext(), MainActivity.db, this);

        binding.navBar.getMenu().findItem(R.id.scanner).setChecked(true);

        if (allPermissionGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    ScanningFragment.this.getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE
            );
        }

        binding.navBar.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case (R.id.person):
                                NavHostFragment.findNavController(ScanningFragment.this)
                                    .navigate(R.id.action_scanningFragment_to_informationFragment);
                                break;
                        }
                        return false;
                    }
                });
        return view;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> providerListenableFuture
                = ProcessCameraProvider.getInstance(this.requireContext());

        providerListenableFuture.addListener(() -> {
                    try {
                        ProcessCameraProvider cameraProvider = providerListenableFuture.get();

                        Preview preview = new Preview.Builder().build();
                        preview.setSurfaceProvider(binding.pvCamera.getSurfaceProvider());

                        CameraSelector cameraSelector = new CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build();
                        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this.getContext()),
                                qrAnalyzer);

                        Camera camera = cameraProvider.bindToLifecycle(
                                this,
                                cameraSelector,
                                preview,
                                imageAnalysis
                        );
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                ContextCompat.getMainExecutor(this.requireContext())
        );
    }

    private boolean allPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                ScanningFragment.this.getContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (allPermissionGranted()) {
                startCamera();
            }
        }
    }
}
