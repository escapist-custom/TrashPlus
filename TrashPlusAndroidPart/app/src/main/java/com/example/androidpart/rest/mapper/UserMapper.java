package com.example.androidpart.rest.mapper;

import android.util.Log;

import com.example.androidpart.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {

    public static User getFromJson(JSONObject jsonObject, String password) throws JSONException {
        JSONObject userObj = (JSONObject) jsonObject.get("user");
        Log.i("USER_IN", userObj.toString());
        return UserMapper.getUserFromJson(userObj, password);
    }

    public static User getUserFromJson(JSONObject jsonObject, String password) throws JSONException {
        return new User(
                jsonObject.getString("nickName"),
                jsonObject.getString("address"),
                jsonObject.getString("email"),
                password
        );
    }

}
