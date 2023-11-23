package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.androidpart.repository.user.TrashPlusContractUser;


@Entity(tableName = "users", indices = {@Index(value = "email", unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_ID)
    private long id;

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_NICK_NAME)
    private String nickName;

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_ADDRESS)
    private String address;

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_EMAIL)
    private String email;

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_PASSWORD)
    private String password;

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_CONTROL_SUM)
    private int controlSum;

    public User(String nickName, String address, String email, String password) {
        this.nickName = nickName;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getControlSum() {
        return controlSum;
    }

    public void setControlSum(int controlSum) {
        this.controlSum = controlSum;
    }
}
