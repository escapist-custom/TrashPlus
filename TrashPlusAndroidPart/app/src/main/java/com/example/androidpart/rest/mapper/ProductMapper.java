package com.example.androidpart.rest.mapper;

import android.util.Log;

import com.example.androidpart.domain.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    private static List<Product> products = new ArrayList<>();
    public static List<Product> getArrayFromJson(JSONArray json) throws JSONException {
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = (JSONObject) json.get(i);
            products.add(new Product(
                    object.getString("nameOfProduct"),
                    object.getLong("productCode"),
                    object.getString("information"),
                    object.getString("linkPhoto")
            ));
        }
        return products;
    }

    public static Product getProductFromJson(JSONObject json) throws JSONException {
        Product product = new Product(
                json.getString("nameOfProduct"),
                json.getLong("productCode"),
                json.getString("information"),
                json.getString("linkPhoto")
        );
        Log.i("PROMAP", product.toString());
        return product;
    }
}
