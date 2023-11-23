package com.example.androidpart.runnable.product;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

import java.util.Set;

public class GetProductsByUserIdRunnable implements Runnable {
    private ProductTrashPlusDao productDao;
    private UserTrashPlusDao userDao;
    private static Set<Product> products;

    public GetProductsByUserIdRunnable(AppDatabase db) {
        this.productDao = db.trashPlusDaoProduct();
        this.userDao = db.trashPlusDaoUser();
    }

    @Override
    public void run() {
        User user = userDao.getUser();
        products = (Set<Product>) productDao.getAllProducts(user.getId());
    }

    public static Set<Product> getProducts() {
        return products;
    }
}
