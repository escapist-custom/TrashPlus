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
    private String address;
    private String email;
    private String password;
    private List<ProductDto> products;

    public static UserDto toDto(User user) {
        List<ProductDto> userDtoProducts;
        if (user.getProducts() != null) {
            userDtoProducts = user.getProducts().stream().map(ProductDto::toDto).collect(Collectors.toList());
        } else {
            userDtoProducts = new ArrayList<>();
        }

        return UserDto.builder()
                .nickName(user.getNickName())
                .address(user.getAddress())
                .email(user.getEmail())
                .password(user.getPassword())
                .products(userDtoProducts)
                .build();
    }

    public static User fromDto(UserDto userDto, List<Product> products) {
        return User.builder()
                .nickName(userDto.getNickName())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .products(products)
                .build();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", products=" + products +
                '}';
    }
}