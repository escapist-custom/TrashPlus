package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//TODO: update class for date base
public class User {

    private long uid;
    private String nickName;
    private String address;
    private String birthDate;
    private String email;
    private String password;

    public User(String nickName, String address, String birthDate, String email, String password) {
        this.nickName = nickName;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
