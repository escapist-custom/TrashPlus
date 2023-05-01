package com.example.androidpart.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.databinding.ScanningFragmentBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class ScanningFragment extends Fragment {

    private ScanningFragmentBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ScanningFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        if (MainActivity.PERMITTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    this.getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE
            );
        }

        binding.navBar.getMenu().findItem(R.id.scanner).setChecked(true);

        binding.navBar.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case (R.id.person):
                                NavHostFragment.findNavController(ScanningFragment.this)
                                        .navigate(R.id.action_scanningFragment_to_informationFragment);
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


                        Camera camera = cameraProvider.bindToLifecycle(
                                this,
                                cameraSelector,
                                preview
                        );
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                ContextCompat.getMainExecutor(this.requireContext())
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
