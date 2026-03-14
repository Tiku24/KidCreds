package com.example.kidcreds.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.kidcreds.data.local.table.UserEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun updateCreateUserAccount(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun loginUserAccount(email: String, password: String): UserEntity?

    @Delete
    suspend fun deleteUserAccount(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserAccountByEmail(email: String): UserEntity?

}