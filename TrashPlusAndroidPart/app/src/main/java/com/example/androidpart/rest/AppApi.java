package com.example.androidpart.rest;

import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;

import java.util.List;
import java.util.Set;

public interface AppApi {

    void findUserByEmail(String email, String password);

    void insert(User user);

    void getProduct(String productCode, String classOfCover);

    void sendProducts(User user, Set<Product> productSet);

    Integer getControlSum(String email);
}
