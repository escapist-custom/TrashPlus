package com.samsung.service;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.rest.dto.ProductDto;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    User update(User user);

    List<Product> getScannedProducts(long id);

    void deleteByEmail(String email);

    // void addProduct(String email, Product product);
}
