package com.samsung.service.impl;

import com.samsung.rest.dto.ProductDto;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductMapper {
    public static ProductDto getProductFromJson(JSONObject json) throws JSONException {
        return ProductDto.builder()
                .nameOfProduct(json.getString("nameOfProduct"))
                .productCode(json.getLong("productCode"))
                .information(json.getString("information"))
                .linkPhoto(json.getString("linkPhoto"))
                .classOfCover(json.getString("classOfCover"))
                .build();
    }

}
