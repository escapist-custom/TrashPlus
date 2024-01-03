package com.samsung.service.impl;

import com.samsung.rest.dto.UserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class UserMapper {
    public static UserDto getUserFromJson(JSONObject jsonObject) throws JSONException {
        return UserDto.builder()
                .nickName(jsonObject.getString("nickName"))
                .email(jsonObject.getString("email"))
                .password(jsonObject.getString("password"))
                .controlSum(Integer.getInteger(jsonObject.getString("controlSum")))
                .build();
    }
}
