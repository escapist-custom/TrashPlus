package com.samsung.service;


import com.samsung.domain.Product;
import com.samsung.rest.dto.ProductDto;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    Product insert(Product product);

    ProductDto findByCode(long code);

    Optional<Product> findProduct(long code);
    List<Product> findAll();
}
