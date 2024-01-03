package com.example.androidpart.runnable.user;


import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.androidpart.MainActivity;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUpdateRunnable implements Runnable {
    private ProductTrashPlusDao productDao;
    private UserTrashPlusDao userDao;
    private Fragment fragment;

    public UserUpdateRunnable(AppDatabase db, Fragment fragment) {
        this.productDao = db.trashPlusDaoProduct();
        this.userDao = db.trashPlusDaoUser();
        this.fragment = fragment;
    }

    @Override
    public void run() {
        User user = userDao.getUser();
        List<Product> productList = productDao.getAllProducts(user.getId());
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
