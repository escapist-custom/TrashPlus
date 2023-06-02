package com.example.androidpart.runnable.product;

import android.util.Log;

import com.example.androidpart.domain.Product;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;

public class InsertRunnableProduct implements Runnable {
    private final ProductTrashPlusDao productDao;
    private final UserTrashPlusDao userDao;
    private Product product;

    public InsertRunnableProduct(AppDatabase db, Product product) {
        this.productDao = db.trashPlusDaoProduct();
        this.userDao = db.trashPlusDaoUser();
        this.product = product;
    }

    @Override
    public void run() {
        Log.i("PRODUCT_RUNNABLE", product.toString());
        product.setUserId(userDao.getUser().getId());
        if (productDao.getProductByBarcode(Long.toString(product.getProductCode())) == null) {
            productDao.insert(product);
        }
    }
}
