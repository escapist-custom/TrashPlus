package com.samsung.service;

import com.samsung.domain.Product;
import com.samsung.domain.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    User update(User user);

    Set<Product> getScannedProducts(long id);

    void deleteByEmail(String email);

}
