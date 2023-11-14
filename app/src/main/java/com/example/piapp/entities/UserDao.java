package com.example.piapp.entities;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where email=(:email) and password=(:password)")
    UserEntity login(String email , String password);


    @Update
    void updateUser(UserEntity userEntity);

}
