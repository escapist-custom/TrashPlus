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
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
        List<Product> newProducts = new ArrayList<>();
        if (userDto.getProducts() != null) {
            for (ProductDto productDto : userDto.getProducts()) {
                newProducts.add(ProductDto.fromDtoToProduct(productDto));
            }
        }
        System.out.println(userDto.toString());
        return UserDto.toDto(userService.save(UserDto.fromDto(userDto, newProducts)));
    }

    @Transactional
    @PostMapping("/user/{email}")
    public User addProduct(
            @PathVariable("email") String email,
            @RequestParam(value = "products") String productsJson
    ) {
        List<Product> newProducts = new ArrayList<>();
        User user = userService.findByEmail(email);

        JSONArray jsonArray = new JSONArray(productsJson);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
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
        user.setProducts(newProducts);
        userService.update(user);
        /*System.out.println(productRepository.findAllMy() + " PRODUCT IN DB");
        System.out.println(userRepository.findByEmail(user.getEmail()) + " USER FROM DB");
        for (int i = 0; i < newProducts.size(); i++) {
            if (productService.findProduct(newProducts.get(i).getId()).isPresent());
                userRepository.addProduct(user.getId(), newProducts.get(i).getId());
        }*/
        return user;
    }

    @GetMapping("/user/scannedProducts/{id}")
    public List<ProductDto> getScannedProducts(@PathVariable long id) {
        List<Product> products = userService.getScannedProducts(id);
        List<ProductDto> productDtos = new ArrayList<>();
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
