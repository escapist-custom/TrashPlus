package com.samsung.rest.controller;

import com.samsung.rest.dto.ProductDto;
import com.samsung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping("/product/{codeProduct}")
    public ProductDto findByCodeProduct(@PathVariable("codeProduct") long codeProduct) {
        return productService.findByCode(codeProduct);
    }
}
