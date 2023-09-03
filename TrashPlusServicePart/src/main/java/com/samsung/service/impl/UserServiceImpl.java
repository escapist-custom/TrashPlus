package com.samsung.service.impl;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.LinkRepository;
import com.samsung.repository.ProductRepository;
import com.samsung.repository.UserRepository;
import com.samsung.rest.dto.ProductDto;
import com.samsung.rest.dto.UserDto;
import com.samsung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final PasswordEncoder passwordEncoder = null;
    @Autowired
    private final LinkRepository linkRepository = null;
    @Autowired
    private final ProductRepository productRepository = null;

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
                .email(user.getEmail())
                .products(new HashSet<>())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        if (newUser.getProducts() != null)
            newUser.cleanProducts();
        for (int i = 0; i < user.getProducts().size(); i++) {
            System.out.println(user.getProducts());
            if (productRepository.findById(user.getProducts().stream().toList().get(i).getProductId()) == null) {
                productRepository.save(user.getProducts().stream().toList().get(i));
            }
            newUser.addProduct(user.getProducts().stream().toList().get(i));
        }
        return newUser;
    }

    @Override
    public Map<String, Object> getUserProducts(User user) {
        List<Product> productList = new ArrayList<>();
        List<Long> productsId = linkRepository.findByUserId(user.getUserId());
        for (int i = 0; i < productsId.size(); i++) {
            productList.add(productRepository.findById(productsId.get(i)).get());
        }
        Set<ProductDto> productDtos = new HashSet<>();
        for (int i = 0; i < productList.size(); i++) {
            productDtos.add(ProductDto.toDto(productList.get(i)));
        }
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
