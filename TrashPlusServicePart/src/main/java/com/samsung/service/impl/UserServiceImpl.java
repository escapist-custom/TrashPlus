package com.samsung.service.impl;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.UserRepository;
import com.samsung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
                .address(user.getAddress())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .products(user.getProducts())
                .build();
        /*for (int i = 0; i < newUser.getProducts().size(); i++) {
            linkRepository.addProduct(newUser.getId(), newUser.getProducts().get(i).getId());
        }*/
        return newUser;
    }

    @Override
    public List<Product> getScannedProducts(long id) {
        return userRepository.findByProducts(id);
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
