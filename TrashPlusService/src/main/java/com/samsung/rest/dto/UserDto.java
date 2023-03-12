package com.samsung.rest.dto;

import com.samsung.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class UserDto {
    private final String email;
    private final String nickName;
    private final String password;
    private final String role;

    public static UserDto toDto(User user) {
        return UserDto.builder().email(user.getEmail()).nickName(user.getNickName())
                .password(user.getPassword()).role(user.getRole()).build();
    }

    public static User fromDto(UserDto userDto) {
        return User.builder().email(userDto.getEmail()).nickName(userDto.getNickName())
                .password(userDto.getPassword()).role(userDto.getRole()).build();
    }
}
