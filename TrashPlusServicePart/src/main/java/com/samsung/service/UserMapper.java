package com.samsung.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.samsung.domain.User;

public class UserMapper {
	
	public static User getFromJson(JSONObject jsonObject) throws JSONException {
		return User.builder()
				.nickName(jsonObject.getString("nickName")) 
				.email(jsonObject.getString("email"))
				.password(jsonObject.getString("password"))
				.controlSum(jsonObject.getInt("controlSum"))
				.build();
	}
	
}
