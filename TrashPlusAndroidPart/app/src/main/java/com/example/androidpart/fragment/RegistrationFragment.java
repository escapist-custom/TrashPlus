package com.example.androidpart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.R;
import com.example.androidpart.domain.User;
import com.example.androidpart.rest.impl.AppApiVolley;

public class RegistrationFragment extends Fragment {

    private EditText et_nickName;
    private EditText et_address;
    private EditText et_birthDate;
    private EditText et_email;
    private EditText et_password;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);
        et_nickName = view.findViewById(R.id.et_registration_nick_name);
        et_address = view.findViewById(R.id.et_registration_address);
        et_birthDate = view.findViewById(R.id.et_registration_birth_date);
        et_email = view.findViewById(R.id.et_registration_email);
        et_password = view.findViewById(R.id.et_registration_password);
        AppCompatButton bt_signUp = view.findViewById(R.id.bt_registration_sign_up);
        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppApiVolley(RegistrationFragment.this).insert(
                        new User(et_nickName.getText().toString(), et_address.getText().toString(),
                                et_birthDate.getText().toString(), et_email.getText().toString(),
                                et_password.getText().toString()));
            }
        });
        return view;
    }
    public void signIn(){
        NavHostFragment.findNavController(RegistrationFragment.this)
                .navigate(R.id.action_registrationFragment_to_informationFragment);
    }
    public void makeToastFailedRegistration(){
        Toast.makeText(getContext(), "Error during registration ", Toast.LENGTH_SHORT).show();
    }
}
