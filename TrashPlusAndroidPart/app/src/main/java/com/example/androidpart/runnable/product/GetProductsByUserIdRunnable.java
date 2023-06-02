package com.example.androidpart.runnable.product;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

import java.util.List;

public class GetProductsByUserIdRunnable implements Runnable {
    private ProductTrashPlusDao productDao;
    private UserTrashPlusDao userDao;
    private static List<Product> products;

    public GetProductsByUserIdRunnable(AppDatabase db) {
        this.productDao = db.trashPlusDaoProduct();
        this.userDao = db.trashPlusDaoUser();
    }

    @Override
    public void run() {
        User user = userDao.getUser();
        products = productDao.getAllProducts(user.getId());
    }

    public static List<Product> getProducts() {
        return products;
    }
}
