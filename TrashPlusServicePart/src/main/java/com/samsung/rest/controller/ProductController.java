package com.samsung.rest.controller;

import com.samsung.rest.dto.ProductDto;
import com.samsung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ProductController {
	
	@Autowired
    private final ProductService productService = null;

    @GetMapping("product/{codeProduct}")
    public ProductDto findByCodeProduct(@PathVariable("codeProduct") long codeProduct) {
        return productService.findByCode(codeProduct);
    }

    @PostMapping("/product/add/{codeProduct}")
    public ProductDto addProduct(@PathVariable("codeProduct") long codeProduct,
                                 @RequestBody ProductDto productDto) {
        return ProductDto.toDto(productService.addProduct(productDto));
    }
}
