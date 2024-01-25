package com.example.androidpart.domain;


import java.util.Set;

public class DataTransfer {
    private static User user;
    private static Set<Product> productSet;

    public DataTransfer() {}

    public void SetUser(User user1) {
        user = user1;
    }

    public void SetProducts(Set<Product> productSet1) {
        productSet = productSet1;
    }

    public User getUser() {
        return user;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }
}
