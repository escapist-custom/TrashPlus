package com.example.androidpart.runnable.product;

import com.example.androidpart.domain.Product;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao_Impl;

import java.util.List;

public class InsertRunnableProducts implements Runnable {
    private final List<Product> inputProducts;
    private final ProductTrashPlusDao productTrashPlusDao;
    private final UserTrashPlusDao userTrashPlusDao;

    public InsertRunnableProducts(List<Product> inputProducts, AppDatabase db) {
        this.inputProducts = inputProducts;
        this.productTrashPlusDao = db.trashPlusDaoProduct();
        this.userTrashPlusDao = db.trashPlusDaoUser();
    }

    @Override
    public void run() {
        productTrashPlusDao.deleteAll();
        if (userTrashPlusDao.getUser() != null) {
            for (int i = 0; i < inputProducts.size(); i++) {
                inputProducts.get(i).setUserId(userTrashPlusDao.getUser().getId());
                productTrashPlusDao.insert(inputProducts.get(i));
            }
        }
    }
}
