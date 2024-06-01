package com.enthe1m.myapplication;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM User WHERE id = :userId")
    User getUserById(int userId);
}


