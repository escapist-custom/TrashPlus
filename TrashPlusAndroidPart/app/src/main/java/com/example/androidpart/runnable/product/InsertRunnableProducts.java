package com.example.androidpart.runnable.product;

import com.example.androidpart.domain.Product;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Product> productList = inputProducts.stream().collect(Collectors.toList());
        productTrashPlusDao.deleteAll();
        if (userTrashPlusDao.getUser() != null) {
            for (int i = 0; i < productList.size(); i++) {
                productList.get(i).setUserId(userTrashPlusDao.getUser().getId());
                productTrashPlusDao.insert(productList.get(i));
            }
        }
    }
}
