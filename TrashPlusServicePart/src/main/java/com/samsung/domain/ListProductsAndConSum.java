package com.samsung.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ListProductsAndConSum {
    private List<Product> products;
    private Integer controlSum;
}
