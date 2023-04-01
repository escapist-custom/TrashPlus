package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidpart.repository.TrashPlusContract;

//TODO: update class for date base

@Entity
public class User {

    @PrimaryKey
    private long uid;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_NICK_NAME)
    private String nickName;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_ADDRESS)
    private String address;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_BIRTH_DATE)
    private String birthDate;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_EMAIL)
    private String email;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_PASSWORD)
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
