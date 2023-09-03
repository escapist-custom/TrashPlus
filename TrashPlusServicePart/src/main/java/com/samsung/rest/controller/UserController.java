package com.samsung.rest.controller;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.rest.dto.ProductDto;
import com.samsung.rest.dto.UserDto;
import com.samsung.service.ProductService;
import com.samsung.service.UserMapper;
import com.samsung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@RestController
public class UserController {
    @Autowired
    private final UserService userService = null;

    @Autowired
    private final ProductService productService = null;


    @GetMapping("/user/{email}")
    public UserDto findUserByEmail(@PathVariable("email") String email) {
        return UserDto.toDto(userService.findByEmail(email));
    }

    @PostMapping("/user")
    public UserDto insertUser(@RequestBody JSONObject userJson) {
        Set<Product> newProducts = new HashSet<>();
        UserDto userDto = UserDto.toDto(UserMapper.getFromJson(userJson));
        if (userDto.getProducts() != null) {
            for (ProductDto productDto : userDto.getProducts()) {
                newProducts.add(ProductDto.fromDtoToProduct(productDto));
            }
        }
        return UserDto.toDto(userService.save(UserDto.fromDto(userDto, newProducts)));
    }

    @Transactional
    @PostMapping("/user/addProduct/{email}")
    public User addProduct(
            @PathVariable("email") String email,
            @RequestBody User userFromApp
    ) {
        User user = userService.findByEmail(email);
        System.out.println("User from app " + userFromApp.toString());

        JSONObject userIn = new JSONObject(userFromApp);
        JSONArray products = userIn.getJSONArray("products");
        for (int i = 0; i < products.length(); i++) {
            JSONObject jsonObject = (JSONObject) products.get(i);
            System.out.println("JSON Object " + jsonObject.toString());
            String nameOfProduct = jsonObject.getString("nameOfProduct");
            String photoLink = jsonObject.getString("linkPhoto");
            String information = jsonObject.getString("information");
            long productCode = jsonObject.getLong("productCode");
            String classOfCover = jsonObject.getString("classOfCover");
            System.out.println(classOfCover);

            Product product = Product.builder()
                    .information(information)
                    .productCode(productCode)
                    .nameOfProduct(nameOfProduct)
                    .linkPhoto(photoLink)
                    .classOfCover(classOfCover)
                    .build();

            if (!user.getProducts().stream().toList().contains(product)) {
                user.addProduct(product);
            }
        }
        user.setNickName(userIn.getString("nickName"));
        // user.setPassword(userIn.getString("password"));
        user.setEmail(userIn.getString("email"));
        user.setControlSum(userIn.getInt("controlSum"));
        userService.update(user);
        return user;
    }

    @Transactional
    @GetMapping("/user/scannedProducts/{email}")
    public Map<String, Object> getUserProducts(@PathVariable(name = "email") String email) {
        User user = userService.findByEmail(email);
        return userService.getUserProducts(user);
    }

    @GetMapping("/getSum/{email}")
    public Map<String, Integer> getControlSum(@PathVariable(name = "email") String email) {
        Map<String, Integer> map = new HashMap<>();
        Integer controlSum = userService.findByEmail(email).getControlSum();
        map.put("controlSum", controlSum);
        return map;
    }

    @DeleteMapping("/user/{email}")
    public void deleteUserByEmail(@PathVariable String email) {
        userService.deleteByEmail(email);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, UserNotFoundException.class})
    public ResponseEntity<String> handlerUserException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}