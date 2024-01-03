package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.androidpart.repository.user.TrashPlusContractUser;

import java.util.HashSet;
import java.util.Set;


@Entity(tableName = "users", indices = {@Index(value = "email", unique = true)})
public class User {

    @ColumnInfo(name = TrashPlusContractUser.UserEntry.COLUMN_CONTROL_SUM, defaultValue = "0")
    private int controlSum;

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

    public int getControlSum() {
        return controlSum;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Product> getNewProducts() {
        return newProducts;
    }

    public User(String nickName, String address, String email, String password) {
        this.nickName = nickName;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    @Ignore
    public User() {};

    @Ignore
    public User(String nickName, String email, String password,
                int controlSum) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.controlSum = controlSum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", controlSum=" + controlSum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Ignore
    private Set<Product> newProducts = new HashSet();

    @Ignore
    public void addNewProduct(Product product) {
        newProducts.add(product);
    }


    public void setControlSum(int controlSum) {
        this.controlSum = controlSum;
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
}
