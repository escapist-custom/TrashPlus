package com.samsung.service.impl;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.domain.UserProduct;
import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.LinkRepository;
import com.samsung.repository.ProductRepository;
import com.samsung.repository.UserRepository;
import com.samsung.rest.dto.ProductDto;
import com.samsung.rest.dto.UserDto;
import com.samsung.service.ProductService;
import com.samsung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository ;
    @Autowired
    private final PasswordEncoder passwordEncoder ;

    private final LinkRepository linkRepository;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("user with email " + user.getEmail() + " already exists");
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "user with email " + email + " was not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User update(User user) {
        User newUser = User.builder()
                .nickName(user.getNickName())
                .userId(user.getUserId())
                .email(user.getEmail())
                .products(user.getProducts())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return newUser;
    }

    @Override
    public Map<String, Object> getUserProducts(User user) {
        List<Product> productList = new ArrayList<>();
        List<Long> productsCodes = linkRepository.findByUserId(user.getUserId());
        for (int i = 0; i < productsCodes.size(); i++) {
            if (productRepository.findById(productsCodes.get(i)).isPresent())
                productList.add(productRepository.findById(productsCodes.get(i)).get());
        }
        System.out.println(productsCodes.toString() + " productIds");
        Set<ProductDto> productDtos = new HashSet<>();
        for (int i = 0; i < productList.size(); i++) {
            productDtos.add(ProductDto.toDto(productList.get(i)));
        }
        System.out.println(productDtos.toString());
        System.out.println("USER " + user);
        Map<String, Object> map = new HashMap<>();
        map.put("user", UserDto.toDto(user));
        map.put("products", productDtos);
        return map;
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
