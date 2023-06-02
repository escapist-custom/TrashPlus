package com.example.androidpart.repository.product.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidpart.domain.Product;
import com.example.androidpart.repository.product.TrashPlusContractProduct;

import java.util.List;

@Dao
public interface ProductTrashPlusDao {

    @Query("SELECT products.id, products.name, products.product_code, products.information, " +
            "products.photo_link, products.user_id, products.cover_code FROM products WHERE " +
            " products.user_id = :id")
    List<Product> getAllProducts(long id);

    @Query("SELECT * FROM products WHERE product_code = :barcode")
    Product getProductByBarcode(String barcode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Update
    void update(Product product);

    @Query("DELETE FROM " + TrashPlusContractProduct.ProductEntry.TABLE_NAME)
    void deleteAll();
}
