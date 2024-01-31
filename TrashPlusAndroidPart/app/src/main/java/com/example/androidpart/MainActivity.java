package com.example.androidpart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidpart.domain.DataTransfer;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.runnable.product.GetProductsByUserIdRunnable;
import com.example.androidpart.runnable.product.InsertRunnableProducts;
import com.example.androidpart.runnable.user.InsertRunnableUser;
import com.example.androidpart.runnable.user.UserUpdateRunnable;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase db;
    public static Integer controlSum = 0;
    private static InsertRunnableUser insertRunnableUser;
    private static InsertRunnableProducts insertRunnableProducts;
    public static String sharedPreferencesName = "SHARED_PREFERENCES";
    public static SharedPreferences sharedPreferences;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(MainActivity.this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.getPrimaryNavigationFragment();
        GetProductsByUserIdRunnable getProductsByUserIdRunnable = new GetProductsByUserIdRunnable(db);
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(getProductsByUserIdRunnable);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserUpdateRunnable userUpdateRunnable = new UserUpdateRunnable(db, fragment,
                getSharedPreferences(sharedPreferencesName, MODE_PRIVATE));
        executorService.execute(userUpdateRunnable);
        service.shutdown();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        Gson gson = new Gson();
        DataTransfer dataTransfer = new DataTransfer();
        Log.i("userName", dataTransfer.getUser().getNickName());
        editor1.putString("userEmail", dataTransfer.getUser().getEmail());
        editor1.putString("userName", dataTransfer.getUser().getNickName());
        editor1.putString("userPassword", dataTransfer.getUser().getPassword());
        editor1.putInt("controlSum", dataTransfer.getUser().getControlSum());
        editor1.putString("products", gson.toJson(dataTransfer.getProductSet()));
        editor1.putBoolean("addedOrNot", true);
        editor1.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferences.getBoolean("addedOrNot", false)) {
            loginWithSharedPref();
        }
    }
    public void loginWithSharedPref() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Product> products = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(sharedPreferences.getString(
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
        user.setEmail(sharedPreferences.getString("userEmail", ""));
        user.setPassword(sharedPreferences.getString("userPassword", ""));
        user.setControlSum(sharedPreferences.getInt("controlSum", 0));
        user.setNickName(sharedPreferences.getString("userName", ""));

        Log.i("sharedPref", sharedPreferences.getString("userEmail", "no_i"));
        Log.i("spProductSet", productSet.toString());
        insertRunnableProducts = new InsertRunnableProducts(productSet, db);
        insertRunnableUser = new InsertRunnableUser(user, db);

        service.execute(insertRunnableUser);
        service.execute(insertRunnableProducts);

        NavHostFragment.findNavController(Objects.requireNonNull(
                        fragmentManager.getPrimaryNavigationFragment()))
                .navigate(R.id.action_loginFragment_to_informationFragment);
    }
}
