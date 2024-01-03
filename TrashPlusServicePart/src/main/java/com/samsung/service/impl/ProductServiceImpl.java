package com.samsung.service.impl;

import com.samsung.domain.Product;
import com.samsung.repository.ProductRepository;
import com.samsung.rest.dto.ProductDto;
import com.samsung.service.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    private JsonReaderImpl jsonReader;
    private final String API_URL = "https://rskrf.ru/rest/1/search/barcode?barcode=";

    @Override
    public ProductDto findByCodeDb(long code, String classOfCover) {
        return ProductDto.toDto(productRepository.findByProductCode(code));
    }

    @Override
    public Product findByCodeAPI(long code, String classOfCover) {
        Product product;
        try {
            JSONObject response = jsonReader.readFromLink(API_URL + code).getJSONObject("response");
            String nameOfProduct = response.getString("title");
            String description = response.getString("description");
            String image = response.getString("thumbnail");
            product = Product.builder()
                    .nameOfProduct(nameOfProduct)
                    .productCode(code)
                    .information(description)
                    .linkPhoto(image)
                    .classOfCover(classOfCover)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Product findProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        return productRepository.save(ProductDto.fromDtoToProduct(productDto));
    }
}
