package com.example.androidpart.repository.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidpart.domain.User;

@Dao
public interface
UserTrashPlusDao {

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM users")
    User getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM users")
    void deleteAll();

}
