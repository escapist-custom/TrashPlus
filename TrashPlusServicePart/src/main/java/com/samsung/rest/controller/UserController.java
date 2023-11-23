package com.samsung.rest.controller;

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
        return UserDto.toDto(userService.findByEmail(email));
    }

    @PostMapping("/user")
    public UserDto insertUser(@RequestBody UserDto userDto) {
        Set<Product> newProducts = new HashSet<>();
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
            @RequestBody Set<Product> productsJson
    ) {
        Set<Product> newProducts = new HashSet<>();
        User user = userService.findByEmail(email);
        System.out.println(productsJson);

        JSONObject userIn = new JSONObject(productsJson);
        System.out.println(userIn);
        JSONArray products = userIn.getJSONArray("products");
        for (int i = 0; i < products.length(); i++) {
            JSONObject jsonObject = (JSONObject) products.get(i);
            String nameOfProduct = jsonObject.getString("nameOfProduct");
            String photoLink = jsonObject.getString("photoLink");
            String information = jsonObject.getString("information");
            long productCode = jsonObject.getLong("productCode");

            Product product = Product.builder()
                    .information(information)
                    .productCode(productCode)
                    .nameOfProduct(nameOfProduct)
                    .linkPhoto(photoLink)
                    .build();
            newProducts.add(product);
        }
        System.out.println(newProducts);
        user.setProducts(newProducts);
        userService.save(user);
        /*System.out.println(productRepository.findAllMy() + " PRODUCT FROM DB");
        System.out.println(userRepository.findByEmail(user.getEmail()) + " USER FROM DB");
        for (int i = 0; i < newProducts.size(); i++) {
            if (productService.findProduct(newProducts.get(i).getId()).isPresent());
                userRepository.addProduct(user.getId(), newProducts.get(i).getId());
        }*/
        return user;
    }

    @GetMapping("/getSum/{email}")
    public Map<String, Integer> getControlSum(@PathVariable(name = "email") String email) {
        Map<String, Integer> map = new HashMap<>();
        Integer controlSum = userService.findByEmail(email).getControlSum();
        map.put("controlSum", controlSum);
        return map;
    }

    @GetMapping("/user/scannedProducts/{id}")
    public Set<ProductDto> getScannedProducts(@PathVariable long id) {
        Set<Product> products = userService.getScannedProducts(id);
        Set<ProductDto> productDtos = new HashSet<>();
        for (Product product : products) {
            ProductDto productDto = ProductDto.toDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
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