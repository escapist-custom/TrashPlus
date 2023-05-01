package com.example.androidpart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.databinding.InformationFragmentBinding;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InformationFragment extends Fragment {

    private TextView tv_userInfo;
    private TextView tv_nickName;
    private TextView tv_address;
    private TextView tv_email;
    private TextView tv_password;
    private Handler handler = new Handler();
    private InformationFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InformationFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ExecutorService service = Executors.newSingleThreadExecutor();

        GetUserRunnable userRunnable = new GetUserRunnable(MainActivity.db);
        service.execute(userRunnable);

        binding.navBar.getMenu().findItem(R.id.person).setChecked(true);

        binding.navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case(R.id.scanner):
                        /*getActivity().getSupportFragmentManager().beginTransaction()
                                .remove(InformationFragment.this).commit();*/
                        NavHostFragment.findNavController(InformationFragment.this).navigate(R.id.action_informationFragment_to_scanningFragment);

                }

                return false;
            }
        });

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
                    binding.userName.setText(userOutput.getNickName());
                    binding.userEmail.setText(userOutput.getEmail());
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
