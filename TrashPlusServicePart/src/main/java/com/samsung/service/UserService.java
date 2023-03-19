package com.samsung.service;

import com.samsung.domain.User;

public interface UserService {
    User save(User user);

    User findByEmail(String email);
}
