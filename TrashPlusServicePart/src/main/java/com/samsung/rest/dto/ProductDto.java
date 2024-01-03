package com.samsung.rest.dto;

import com.samsung.domain.Product;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long productId;
    private String nameOfProduct;
    private long productCode;
    private String information;
    private String linkPhoto;
    private String classOfCover;


    public static Product fromDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.getProductId())
                .nameOfProduct(productDto.getNameOfProduct())
                .productCode(productDto.getProductCode())
                .information(productDto.getInformation())
                .linkPhoto(productDto.getLinkPhoto())
                .classOfCover(productDto.getClassOfCover())
                .build();
    }

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .nameOfProduct(product.getNameOfProduct())
                .productCode(product.getProductCode())
                .information(product.getInformation())
                .linkPhoto(product.getLinkPhoto())
                .classOfCover(product.getClassOfCover())
                .build();
    }
}