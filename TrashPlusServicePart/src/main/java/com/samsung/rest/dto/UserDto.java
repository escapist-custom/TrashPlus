package com.samsung.rest.dto;

import com.samsung.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
@Builder
@Getter
public class UserDto {
    private final String nickName;
    private final String address;
    private final String birthDate;

    private final String email;
    private final String password;

    // TODO: birthDate with Date class, instead of String
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .nickName(user.getNickName())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    // TODO: birthDate with Date class, instead of String
    public static User fromDto(UserDto userDto) {
        return User.builder()
                .nickName(userDto.getNickName())
                .address(userDto.getAddress())
                .birthDate(userDto.getBirthDate())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}