package com.samsung.service.impl;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.repository.ProductRepository;
import com.samsung.repository.UserRepository;
import com.samsung.rest.dto.ProductDto;
import com.samsung.service.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    private static UserRepository userRepositoryStatic;
    private static ProductRepository productRepositoryStatic;
    private JsonReaderImpl jsonReader;
    private final String API_URL = "https://rskrf.ru/rest/1/search/barcode?barcode=";

    @Override
    public Product insert(Product product) {
        return productRepository.save(product);
    }

    @Override
    public ProductDto findByCode(long code) {
        Product product;
        try {
            if (productRepository.findByProductCode(code) != null) {
                return ProductDto.toDto(productRepository.findByProductCode(code));
            } else {
                JSONObject response = jsonReader.readFromLink(API_URL + code).getJSONObject("response");
                String nameOfProduct = response.getString("title");
                String description = response.getString("description");
                String image = response.getString("thumbnail");

                product = Product.builder()
                        .nameOfProduct(nameOfProduct)
                        .productCode(code)
                        .information(description)
                        .linkPhoto(image)
                        .build();
                productRepository.save(product);
            }
            return ProductDto.toDto(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllMy();
    }

}
