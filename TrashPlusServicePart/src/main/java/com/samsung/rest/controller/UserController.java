package com.samsung.rest.controller;

import com.samsung.domain.ListProductsAndConSum;
import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.ProductRepository;
import com.samsung.repository.UserRepository;
import com.samsung.rest.dto.ProductDto;
import com.samsung.rest.dto.UserDto;
import com.samsung.service.ProductService;
import com.samsung.service.UserService;
import com.samsung.service.impl.UserMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequiredArgsConstructor
@RestController
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductService productService;

    @GetMapping("/user/{email}")
    public UserDto findUserByEmail(@PathVariable("email") String email) {
        System.out.println(userService.findByEmail(email).getProducts());
        return UserDto.toDto(userService.findByEmail(email));
    }

    @PostMapping("/user/addUser")
    public UserDto insertUser(@RequestBody UserDto userJson) {
        System.out.println(userJson.toString() + " USER JSON");
        Set<Product> newProducts = new HashSet<>();
        if (userJson.getProducts() != null) {
            for (ProductDto productDto : userJson.getProducts()) {
                newProducts.add(ProductDto.fromDtoToProduct(productDto));
            }
        }
        return UserDto.toDto(userService.save(UserDto.fromDto(userJson, newProducts)));
    }

    @Transactional
    @PostMapping("/user/addProduct/{email}")
    public User addProduct(
            @PathVariable("email") String email,
            @RequestBody ListProductsAndConSum lpcs
            ) {
        User user = userService.findByEmail(email);
        System.out.println(lpcs + " lpcs");
        List<Product> products = lpcs.getProducts();
        System.out.println(products + " LIST IN");
        Integer controlSum = lpcs.getControlSum();
        for (int i = 0; i < products.size(); i++) {
            user.getProducts().add(products.get(i));
        }
        Set<Product> set = new HashSet<>(products);
        user.setProducts(set);
        user.setControlSum(controlSum);
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
        System.out.println(controlSum + " controlSum");
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