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

import com.example.androidpart.R;
import com.example.androidpart.databinding.LoginFragmentBinding;
import com.example.androidpart.rest.AppApi;
import com.example.androidpart.rest.impl.AppApiVolley;


public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.btLoginSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppApi volley = new AppApiVolley(LoginFragment.this);
                volley.findUserByEmail(binding.etLoginEmail.getText().toString(),
                        binding.etLoginPassword.getText().toString());
            }
        });
        binding.btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });
        return view;
    }
    public void login(){
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_informationFragment);
    }

    public void makeToastBadCredentials(){
        Toast.makeText(getContext(), "Error in login or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
