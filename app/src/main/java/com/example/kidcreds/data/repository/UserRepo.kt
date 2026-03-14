package com.example.kidcreds.data.repository

import com.example.kidcreds.data.local.dao.UserDao
import com.example.kidcreds.data.local.table.UserEntity
import javax.inject.Inject

interface UserRepo {
    suspend fun updateCreatUserAccount(userEntity: UserEntity)
    suspend fun loginUserAccount(email: String, password: String): UserEntity?
}