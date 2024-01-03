package com.samsung.rest.controller;

import com.samsung.rest.dto.ProductDto;
import com.samsung.service.ProductService;
import com.samsung.service.impl.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping("productDb/{codeProduct}/{classOfCover}")
    public ProductDto findByCodeProduct(@PathVariable("codeProduct") long codeProduct,
                                        @PathVariable("classOfCover") String classOfCover) {
        return productService.findByCodeDb(codeProduct, classOfCover);
    }

    @GetMapping("productAPI/{codeProduct}/{classOfCover}")
    public ProductDto findByCodeProductAPI(@PathVariable("codeProduct") long codeProduct,
        @PathVariable("classOfCover") String classOfCover) {
        System.out.println(productService.findByCodeAPI(codeProduct, classOfCover));
        return ProductDto.toDto(productService.findByCodeAPI(codeProduct, classOfCover));
    }

    @PostMapping("/product/add/{codeProduct}")
    public ProductDto addProduct(@PathVariable("codeProduct") long codeProduct,
                                 @RequestBody JSONObject productJson) {
        return ProductDto.toDto(productService.addProduct(ProductMapper.getProductFromJson(productJson)));
    }
}