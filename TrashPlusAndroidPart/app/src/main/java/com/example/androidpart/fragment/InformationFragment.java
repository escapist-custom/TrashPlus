package com.example.androidpart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.androidpart.R;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.TrashPlusContract;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.example.androidpart.thread.FindBy;

public class InformationFragment extends Fragment {
    private AppDatabase db;


    private TextView tv_userInfo;
    private TextView tv_nickName;
    private TextView tv_address;
    private TextView tv_email;
    private TextView tv_password;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment, container, false);
        new AppApiVolley(this).getUserInfo();
        tv_userInfo = view.findViewById(R.id.tv_info_user_info);
        tv_nickName = view.findViewById(R.id.tv_info_user_nickname);
        tv_email = view.findViewById(R.id.tv_info_user_email);
        tv_password = view.findViewById(R.id.tv_info_user_password);

        db = Room.databaseBuilder(this.getContext(),
                AppDatabase.class, TrashPlusContract.TrashEntry.DATABASE_NAME).build();

        Data data = new Data.Builder()
                .putString("Email", user.getEmail())
                .build();

        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(FindBy.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(this.getContext()).enqueue(work);

        WorkManager.getInstance(this.getContext()).getWorkInfoByIdLiveData(work.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            user = new User(
                                    workInfo.getOutputData().getString("Nickname"),
                                    workInfo.getOutputData().getString("Address"),
                                    workInfo.getOutputData().getString("Email"),
                                    workInfo.getOutputData().getString("Password")
                            );
                        }
                    }
                });

        //------------------------------------------------------------------
        tv_nickName.setText("Hello, " + user.getNickName() + "!");
        tv_address.setText("Address: " + user.getAddress());
        tv_birthDate.setText("Birth date: " + user.getBirthDate());
        tv_email.setText("Email: " + user.getEmail());
        tv_password.setText("Password: " + user.getPassword());
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
