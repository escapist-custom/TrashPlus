package com.samsung.rest.dto;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class UserDto {
    private String nickName;
    private String email;
    private String password;
    private Set<ProductDto> products;
    private int controlSum;

    public static UserDto toDto(User user) {
        Set<ProductDto> userDtoProducts;
        if (user.getProducts() != null) {
            userDtoProducts = user.getProducts().stream().map(ProductDto::toDto).collect(Collectors.toSet());
        } else {
            userDtoProducts = new HashSet<>();
        }

        return UserDto.builder()
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword())
                .products(userDtoProducts)
                .controlSum(user.getControlSum())
                .build();
    }

    public static User fromDto(UserDto userDto, Set<Product> products) {
        return User.builder()
                .nickName(userDto.getNickName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .products(products)
                .controlSum(userDto.getControlSum())
                .build();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", products=" + products +
                ", controlSum=" + controlSum +
                '}';
    }
}