package com.example.androidpart.cache;

import com.example.androidpart.domain.User;

public class UserCache {

    private static User CURRENT_USER;

    public static User getCurrentUser() {
        return CURRENT_USER;
    }

    public static void setCurrent_user(User current_user) {
        CURRENT_USER = current_user;
    }
}
