package com.samsung.service;

import com.samsung.domain.User;

import java.util.Map;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    User update(User user);

    Map<String, Object> getUserProducts(User user);

    void deleteByEmail(String email);

}
