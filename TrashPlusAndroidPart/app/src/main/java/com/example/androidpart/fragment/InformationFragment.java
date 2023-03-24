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
// import com.example.androidpart.rest.AppApiVolley;

import org.json.JSONException;

public class InformationFragment extends Fragment {

    private TextView tv_hello;
    private TextView tv_userInfo;
    private TextView tv_adminInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment, container, false);
        /*new AppApiVolley(this).getUserInformation();
        new AppApiVolley(this).getAdminInformation();*/
        tv_userInfo = view.findViewById(R.id.tv_info_user_information);
        tv_hello = view.findViewById(R.id.tv_info_hello);
        tv_hello.setText("Hello, " + UserCache.getCurrentUser().getNickName() + "!");
        return view;
    }

    public void setUserInfo(String userInfo){
        tv_userInfo.setText(userInfo);
    }

    public void setAdminInfo(String adminInfo){
        tv_adminInfo.setText(adminInfo);
    }
}
