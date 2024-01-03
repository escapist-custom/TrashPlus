package com.example.androidpart.runnable.product;

import android.util.Log;

import com.example.androidpart.MainActivity;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

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
        User user = userDao.getUser();
        product.setUserId(user.getId());
        user.addNewProduct(product);
        if (productDao.getProductByBarcode(Long.toString(product.getProductCode())) == null) {
            MainActivity.controlSum += 5;
            Log.i("CONTROL_SUM", Integer.toString(MainActivity.controlSum));
            productDao.insert(product);
        }
    }
}
