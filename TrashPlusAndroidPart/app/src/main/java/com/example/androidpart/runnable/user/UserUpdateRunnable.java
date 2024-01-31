package com.example.androidpart.runnable.user;


import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUpdateRunnable implements Runnable {
    private ProductTrashPlusDao productDao;
    private UserTrashPlusDao userDao;
    private Fragment fragment;
    private SharedPreferences sharedPreferences;

    public UserUpdateRunnable(AppDatabase db, Fragment fragment,
                              SharedPreferences sharedPreferences) {
        this.productDao = db.trashPlusDaoProduct();
        this.userDao = db.trashPlusDaoUser();
        this.fragment = fragment;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void run() {
        User user = userDao.getUser();
        List<Product> productList = productDao.getAllProducts();
        Gson gson = new Gson();
        String stringUserProducts = gson.toJson(productList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", user.getNickName());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userPassword", user.getPassword());
        editor.putInt("controlSum", user.getControlSum());
        editor.putString("products", stringUserProducts);
        editor.putBoolean("addedOrNot", true);
        editor.apply();
        Log.i("sharedPref", sharedPreferences.getString("products",
                "no"));
        Set<Product> productsSet = productList.stream().collect(Collectors.toSet());
        Log.i("TRUE_OR_FALSE", String.valueOf(Objects.equals(
                new AppApiVolley(fragment).getControlSum(user.getEmail()),
                MainActivity.controlSum)));
        if (!Objects.equals(new AppApiVolley(fragment).getControlSum(user.getEmail()),
                MainActivity.controlSum)) {
            new AppApiVolley(fragment).sendProducts(user, productsSet);
        }
    }
}
