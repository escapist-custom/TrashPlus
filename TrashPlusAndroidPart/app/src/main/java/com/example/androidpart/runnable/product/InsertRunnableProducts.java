package com.example.androidpart.runnable.product;

import android.util.Log;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InsertRunnableProducts implements Runnable {
    private final Set<Product> inputProducts;
    private final ProductTrashPlusDao productTrashPlusDao;
    private final UserTrashPlusDao userTrashPlusDao;

    public InsertRunnableProducts(Set<Product> inputProducts, AppDatabase db) {
        this.inputProducts = inputProducts;
        this.productTrashPlusDao = db.trashPlusDaoProduct();
        this.userTrashPlusDao = db.trashPlusDaoUser();
    }

    @Override
    public void run() {
        List<Product> productList = new ArrayList<>(inputProducts);
        Log.i("productList", productList.toString());
        productTrashPlusDao.deleteAll();
        User user = userTrashPlusDao.getUser();
        List<Product> allProducts = productTrashPlusDao.getAllProducts();
        Log.i("products", allProducts.toString());
        if (user != null) {
            for (int i = 0; i < productList.size(); i++) {
                if (!allProducts.contains(productList.get(i))) {
                    productList.get(i).setUserId(user.getId());
                    productTrashPlusDao.insert(productList.get(i));
                }
            }
        }
    }
}
