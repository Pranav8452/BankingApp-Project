package com.example.bankingapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    // Insert new user
    @Insert
    void insert(User user);

    // Get all users
    @Query("SELECT * FROM users")   // ✅ fixed
    List<User> getAllUsers();

    // Get single user by email
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")  // ✅ fixed
    User getUserByEmail(String email);

    // Get single user by email & password
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")  // ✅ fixed
    User getUserByEmailAndPassword(String email, String password);

    // Update existing user
    @Update
    void updateUser(User user);
}
