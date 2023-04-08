package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.androidpart.repository.TrashPlusContract;

//TODO: update class for date

@Entity(indices = {@Index(value = "email", unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_NICK_NAME)
    private String nickName;

    @ColumnInfo(name = TrashPlusContract.TrashEntry.COLUMN_ADDRESS)
    private String address;

    //TODO: update it for working with Data class
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
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
