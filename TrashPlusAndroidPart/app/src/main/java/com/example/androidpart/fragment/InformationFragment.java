package com.example.androidpart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.dao.UserTrashPlusDao;
import com.example.androidpart.runnable.InsertRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InformationFragment extends Fragment {

    private TextView tv_userInfo;
    private TextView tv_nickName;
    private TextView tv_address;
    private TextView tv_email;
    private TextView tv_password;
    private User user;
    private UserTrashPlusDao userDao;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment, container, false);
        tv_userInfo = view.findViewById(R.id.tv_info_user_info);
        tv_address = view.findViewById(R.id.tv_info_address);
        tv_nickName = view.findViewById(R.id.tv_info_user_nickname);
        tv_email = view.findViewById(R.id.tv_info_user_email);
        tv_password = view.findViewById(R.id.tv_info_user_password);

        ExecutorService service = Executors.newSingleThreadExecutor();

        GetUserRunnable userRunnable = new GetUserRunnable(MainActivity.db);
        service.execute(userRunnable);

        return view;
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

    public void setTv_email(TextView tv_email) {
        this.tv_email = tv_email;
    }

    public void setTv_password(TextView tv_password) {
        this.tv_password = tv_password;
    }

    class GetUserRunnable implements Runnable {
        private User userOutput;
        private final UserTrashPlusDao userDao;

        Handler userHandler;
        public GetUserRunnable(AppDatabase database) {
            userDao = database.trashPlusDao();
        }
        @Override
        public void run() {
            userOutput = userDao.getUser();
            Log.i("USER", userOutput.toString() + 1);
            userHandler = new Handler(Looper.getMainLooper());
            userHandler.post(new Runnable() {
                @Override
                public void run() {
                    tv_nickName.setText("Hello, " + userOutput.getNickName() + "!");
                    tv_address.setText("Address: " + userOutput.getAddress());
                    tv_email.setText("Email: " + userOutput.getEmail());
                    tv_password.setText("Password: " + userOutput.getPassword());
                }
            });
        }
    }
}
