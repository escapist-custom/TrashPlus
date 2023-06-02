package com.example.androidpart.rest;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;

import java.util.List;

public interface AppApi {

    void findUserByEmail(String email, String password);

    void insert(User user);

    void getProduct(String productCode);

    void updateUser(User user, List<Product> products);

}
