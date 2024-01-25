package com.example.androidpart.runnable.product;

import com.example.androidpart.domain.DataTransfer;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Product> productList = productDao.getAllProducts(user.getId());
        products = productList.stream().collect(Collectors.toSet());
        DataTransfer dataTransfer = new DataTransfer();
        dataTransfer.SetProducts(products);
    }
}
