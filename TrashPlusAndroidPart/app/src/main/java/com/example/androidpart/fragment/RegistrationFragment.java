package com.example.androidpart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.databinding.RegistrationFragmentBinding;
import com.example.androidpart.domain.User;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.example.androidpart.runnable.user.InsertRunnableUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrationFragment extends Fragment {
    private ExecutorService service;
    private RegistrationFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RegistrationFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        service = Executors.newSingleThreadExecutor();
        binding.btRegistrationSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(
                        binding.etRegistrationNickName.getText().toString(),
                        binding.etRegistrationEmail.getText().toString(),
                        binding.etRegistrationPassword.getText().toString());
                service.execute(new InsertRunnableUser(user, MainActivity.db));
                new AppApiVolley(RegistrationFragment.this).insert(user);
            }
        });
        return view;
    }

    public void signIn() {
        NavHostFragment.findNavController(RegistrationFragment.this)
                .navigate(R.id.action_registrationFragment_to_informationFragment);
    }

    public void makeToastFailedRegistration() {
        Toast.makeText(getContext(), "Error during registration ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
