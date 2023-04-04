package com.example.androidpart.repository;

import static android.graphics.Region.Op.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidpart.domain.User;

import java.util.List;

@Dao
public interface TrashPlusDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM user")
    User getUser();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}
