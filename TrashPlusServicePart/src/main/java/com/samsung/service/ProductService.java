package com.samsung.service;


import com.samsung.domain.Product;
import com.samsung.rest.dto.ProductDto;
import jakarta.persistence.SecondaryTable;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ProductService {


    ProductDto findByCode(long code);

    Optional<Product> findProduct(long code);

    Product addProduct(ProductDto productDto);
}
