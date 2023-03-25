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
import com.example.androidpart.cache.UserCache;
import com.example.androidpart.domain.User;
// import com.example.androidpart.rest.AppApiVolley;

import org.json.JSONException;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        AppCompatButton bt_login = view.findViewById(R.id.bt_login_sign_in);
        AppCompatButton bt_registration = view.findViewById(R.id.bt_login_sign_up);
        EditText et_email = view.findViewById(R.id.et_login_email);
        EditText et_password = view.findViewById(R.id.et_login_password);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {
                    AppApiVolley volley = new AppApiVolley(LoginFragment.this);
                    volley.findUserByEmail(et_email.getText().toString(),
                            et_password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
        bt_registration.setOnClickListener(new View.OnClickListener() {
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
        Toast.makeText(getContext(), "Ошибка в логине или пароле", Toast.LENGTH_SHORT).show();
    }
}
