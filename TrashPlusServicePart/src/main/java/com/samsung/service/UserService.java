package com.samsung.service;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import jakarta.persistence.SecondaryTable;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    User update(User user);

    List<Product> getScannedProducts(long id);

    void deleteByEmail(String email);

}
