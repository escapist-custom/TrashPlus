package com.samsung.rest.dto;

import com.samsung.domain.Product;
import com.samsung.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
@Getter
public class UserDto {
    private final String nickName;
    private final String address;
    private final String email;
    private final String password;
    private final List<ProductDto> products;

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