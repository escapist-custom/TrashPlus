package com.example.androidpart.fragment;

import static com.example.androidpart.MainActivity.db;

import android.os.Bundle;
import android.util.Log;
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
import com.example.androidpart.databinding.LoginFragmentBinding;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.rest.AppApi;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.example.androidpart.runnable.product.InsertRunnableProducts;
import com.example.androidpart.runnable.user.InsertRunnableUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;
    public static String sharedPreferencesName = "SHARED_PREFERENCES";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.btLoginSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.sharedPreferences.getString("userEmail", "")
                        .equals(binding.etLoginEmail.getText().toString())) {
                    loginWithSharedPref();
                } else {
                    AppApi volley = new AppApiVolley(LoginFragment.this);
                    volley.findUserByEmail(binding.etLoginEmail.getText().toString(),
                            binding.etLoginPassword.getText().toString());
                }
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

    public void login() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_informationFragment);
    }

    public void makeToastBadCredentials() {
        Toast.makeText(getContext(), "Error in login or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loginWithSharedPref() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Product> products = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(MainActivity.sharedPreferences.getString(
                    "products",
                    "{}"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                Product product = new Product(
                        object.getString("nameOfProduct"),
                        object.getLong("productCode"),
                        object.getString("information"),
                        object.getString("photoLink"),
                        object.getString("classOfCover"));
                products.add(product);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Set<Product> productSet = new HashSet<>(products);
        Log.i("sharedPref", products.toString());

        // User
        User user = new User();
        user.setEmail(MainActivity.sharedPreferences.getString("userEmail", ""));
        user.setPassword(MainActivity.sharedPreferences.getString("userPassword", ""));
        user.setControlSum(MainActivity.sharedPreferences.getInt("controlSum", 0));
        user.setNickName(MainActivity.sharedPreferences.getString("userName", ""));

        Log.i("sharedPref", MainActivity.sharedPreferences
                .getString("userEmail", "no_i"));
        Log.i("spProductSet", productSet.toString());
        InsertRunnableProducts insertRunnableProducts = new InsertRunnableProducts(productSet, db);
        InsertRunnableUser insertRunnableUser = new InsertRunnableUser(user, db);

        service.execute(insertRunnableUser);
        service.execute(insertRunnableProducts);

        login();
    }
}
