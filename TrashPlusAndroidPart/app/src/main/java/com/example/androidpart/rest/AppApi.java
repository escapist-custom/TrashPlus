package com.example.androidpart.rest;

import com.example.androidpart.domain.User;

public interface AppApi {

    void findUserByEmail(String email, String password);

    void insert(User user);

}
