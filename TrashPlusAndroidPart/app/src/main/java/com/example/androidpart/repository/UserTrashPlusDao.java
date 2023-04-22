package com.example.androidpart.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidpart.domain.User;

import java.util.List;

@Dao
public interface UserTrashPlusDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM users")
    User getUser();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}
