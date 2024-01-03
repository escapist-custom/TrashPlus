package com.samsung.service;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.rest.dto.ProductDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    User update(User user);

    Map<String, Object> getUserProducts(User user);

    void deleteByEmail(String email);
}
