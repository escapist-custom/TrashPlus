package com.example.androidpart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidpart.R;
import com.example.androidpart.cache.UserCache;
import com.example.androidpart.domain.User;
import com.example.androidpart.rest.impl.AppApiVolley;

import org.json.JSONException;

public class InformationFragment extends Fragment {

    private TextView tv_userInfo;
    private TextView tv_nickName;
    private TextView tv_address;
    private TextView tv_birthDate;
    private TextView tv_email;
    private TextView tv_password;

    // TODO: transfer to SQLite database
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment, container, false);
        new AppApiVolley(this).getUserInfo();
        tv_userInfo = view.findViewById(R.id.tv_info_user_info);
        tv_nickName = view.findViewById(R.id.tv_info_user_nickname);
        tv_address = view.findViewById(R.id.tv_info_user_address);
        tv_birthDate = view.findViewById(R.id.tv_info_user_birthDate);
        tv_email = view.findViewById(R.id.tv_info_user_email);
        tv_password = view.findViewById(R.id.tv_info_user_password);
        //----------------
        tv_nickName.setText("Hello, " + UserCache.getCurrentUser().getNickName() + "!");
        tv_address.setText("Address: " + UserCache.getCurrentUser().getAddress());
        tv_birthDate.setText("Birth date: " + UserCache.getCurrentUser().getBirthDate());
        tv_email.setText("Email: " + UserCache.getCurrentUser().getEmail());
        tv_password.setText("Password: " + UserCache.getCurrentUser().getPassword());
        return view;
    }

    public void setInfo(String info) {
        tv_userInfo.setText(info);
    }

    public void setTv_userInfo(TextView tv_userInfo) {
        this.tv_userInfo = tv_userInfo;
    }

    public void setTv_nickName(TextView tv_nickName) {
        this.tv_nickName = tv_nickName;
    }

    public void setTv_address(TextView tv_address) {
        this.tv_address = tv_address;
    }

    public void setTv_birthDate(TextView tv_birthDate) {
        this.tv_birthDate = tv_birthDate;
    }

    public void setTv_email(TextView tv_email) {
        this.tv_email = tv_email;
    }

    public void setTv_password(TextView tv_password) {
        this.tv_password = tv_password;
    }
}
