package com.example.androidpart.rest.mapper;

import com.example.androidpart.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {

    public static User getFromJson(JSONObject jsonObject, String password) throws JSONException {
        return new User(jsonObject.getString("nickName"),
                jsonObject.getString("address"),
                jsonObject.getString("birthDate"),
                jsonObject.getString("email"),
                password);
    }

}
