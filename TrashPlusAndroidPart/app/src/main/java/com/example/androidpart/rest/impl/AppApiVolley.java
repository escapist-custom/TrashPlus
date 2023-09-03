package com.example.androidpart.rest.impl;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidpart.MainActivity;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.fragment.LoginFragment;
import com.example.androidpart.fragment.RegistrationFragment;
import com.example.androidpart.rest.AppApi;
import com.example.androidpart.rest.mapper.ProductMapper;
import com.example.androidpart.rest.mapper.UserMapper;
import com.example.androidpart.runnable.product.InsertRunnableProduct;
import com.example.androidpart.runnable.product.InsertRunnableProducts;
import com.example.androidpart.runnable.user.InsertRunnableUser;
import com.example.androidpart.service.QRAnalyzer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class AppApiVolley implements AppApi {

    private static final String BASE_URL = "http://192.168.1.49:8080";
    public static final String DATA_SAVED = "DATA_SAVED";
    private Fragment fragment;
    private InsertRunnableUser insertRunnableUser;
    private InsertRunnableProducts insertRunnableProducts;
    private InsertRunnableProduct insertRunnableProduct;
    public static boolean requestFlag = true;

    public AppApiVolley(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void findUserByEmail(String email, String password) {

        ExecutorService service = Executors.newFixedThreadPool(2);

        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user/scannedProducts/" + email;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    User user = UserMapper.getFromJson(response.getJSONObject("user"),
                            password);
                    JSONArray productsJson = response.getJSONArray("products");
                    Log.i("PRODUCTS_IN", productsJson.toString());
                    Set<Product> products = ProductMapper.getArrayFromJson(productsJson);

                    insertRunnableProducts = new InsertRunnableProducts(products, MainActivity.db);
                    insertRunnableUser = new InsertRunnableUser(user, MainActivity.db);

                    service.execute(insertRunnableUser);
                    service.execute(insertRunnableProducts);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                if (fragment.getClass().equals(LoginFragment.class)) {
                    ((LoginFragment) fragment).login();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("BadCredentialsAppApi", error.toString());
                if (fragment.getClass().equals(LoginFragment.class)) {
                    ((LoginFragment) fragment).makeToastBadCredentials();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = email + ":" + password;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " +
                        Base64.encodeToString(credentials.getBytes(),
                                Base64.NO_WRAP));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void insert(User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user/" + user.getEmail();
        JSONObject params = new JSONObject();
        try {
            params.put("nickName", user.getNickName());
            params.put("email", user.getEmail());
            params.put("password", user.getPassword());
            params.put("controlSum", user.getControlSum());
            params.put("products", user.getNewProducts());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.i("Params", params.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (fragment.getClass().equals(RegistrationFragment.class)) {
                            ((RegistrationFragment) fragment).signIn();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (fragment.getClass().equals(RegistrationFragment.class))
                    ((RegistrationFragment) fragment).makeToastFailedRegistration();
                Log.i("API_FAILED_REGISTRATION", user.getEmail());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void getProduct(String productCode) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        QRAnalyzer qrAnalyzer = new QRAnalyzer(fragment.getContext(),
                MainActivity.db, fragment);

        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/product/" + productCode;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Product product = ProductMapper.getProductFromJson(response);
                            product.setJustAdded(true);
                            insertRunnableProduct = new InsertRunnableProduct(MainActivity.db,
                                    product);

                            requestFlag = false;

                            if (qrAnalyzer.scanFlag) {
                                if (qrAnalyzer.result.getText().split("/").length != 2) {
                                    qrAnalyzer.launchDialogError();
                                } else {
                                    qrAnalyzer.launchDialogSuccess(product);
                                }
                                qrAnalyzer.scanFlag = false;
                            } else {
                                getProduct(productCode);
                            }
                            service.execute(insertRunnableProduct);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERRORPRODUCT", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void updateUser(User user, Set<Product> productsSet) {
        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user/addProduct/" + user.getEmail();
        JSONObject params = new JSONObject();
        JSONArray productsJson = new JSONArray();
        List<Product> productList = productsSet.stream().collect(Collectors.toList());
        Log.i("PRODUCT LIST", productList.toString());
        for (int i = 0; i < productList.size(); i++) {
            JSONObject product = new JSONObject();
            try {
                if (productList.get(i).isJustAdded()) {
                    product.put("nameOfProduct", productList.get(i).getNameOfProduct());
                    product.put("productCode", productList.get(i).getProductCode());
                    product.put("information", productList.get(i).getInformation());
                    product.put("linkPhoto", productList.get(i).getPhotoLink());
                    product.put("classOfCover", productList.get(i).getClassOfCover());
                    Log.i("classOfCOver", productList.get(i).getClassOfCover());
                    productsJson.put(product);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            params.put("nickName", user.getNickName());
            params.put("email", user.getEmail());
            params.put("password", user.getPassword());
            params.put("products", productsJson);
            params.put("controlSum", MainActivity.controlSum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.POST,
                url, params,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.i(DATA_SAVED, response.toString());
                        user.setNewProducts(new HashSet<>());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("PARAMS_ERROR", error.getStackTrace().toString());
                        Toast.makeText(fragment.getContext(), "Не удалось сохранить данные",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public Integer getControlSum(String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/getSum/" + email;
        final Integer[] globalControlSum = {0};
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Integer controlSum;
                try {
                    controlSum = response.getInt("controlSum");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                globalControlSum[0] = controlSum;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
        return globalControlSum[0];
    }
}
