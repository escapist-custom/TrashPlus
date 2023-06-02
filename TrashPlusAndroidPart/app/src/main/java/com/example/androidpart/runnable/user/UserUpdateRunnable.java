package com.example.androidpart.runnable.user;


import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;

import java.util.List;

public class UserUpdateRunnable implements Runnable {
    public static final String USER_UPDATE = "USER_UPDATE";
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
        Log.i(USER_UPDATE, user.toString());
        List<Product> products = productDao.getAllProducts(user.getId());
        Log.i(USER_UPDATE, products.toString());
        new AppApiVolley(fragment).updateUser(user, products);
    }
}
