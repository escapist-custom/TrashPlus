package com.example.androidpart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.adapter.ProductAdapter;
import com.example.androidpart.databinding.InformationFragmentBinding;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.runnable.product.GetProductsByUserIdRunnable;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class InformationFragment extends Fragment {
    private InformationFragmentBinding binding;

    private Set<Product> userProduct = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InformationFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ExecutorService service = Executors.newFixedThreadPool(2);

        GetUserAndProductRunnable userProductRunnable = new GetUserAndProductRunnable(MainActivity.db);
        GetProductsByUserIdRunnable productsRunnable = new GetProductsByUserIdRunnable(MainActivity.db);
        service.execute(userProductRunnable);
        service.execute(productsRunnable);

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
        service.shutdown();
        return view;
    }

    class GetUserAndProductRunnable implements Runnable {
        private User userOutput;
        private final UserTrashPlusDao userDao;
        private final ProductTrashPlusDao productDao;

        private Handler userHandler;

        public GetUserAndProductRunnable(AppDatabase database) {
            userDao = database.trashPlusDaoUser();
            productDao = database.trashPlusDaoProduct();
        }

        @Override
        public void run() {
            userOutput = userDao.getUser();
            List<Product> productList = productDao.getAllProducts(userOutput.getId());
            userProduct = productList.stream().collect(Collectors.toSet());
            userHandler = new Handler(Looper.getMainLooper());
            userHandler.post(new Runnable() {
                @Override
                public void run() {
                    binding.userName.setText(userOutput.getNickName());
                    binding.userEmail.setText(userOutput.getEmail());
                    binding.rvProducts.setAdapter(new ProductAdapter(userProduct));
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
