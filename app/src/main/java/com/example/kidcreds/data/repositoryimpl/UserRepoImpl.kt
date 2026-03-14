package com.example.kidcreds.data.repositoryimpl

import com.example.kidcreds.data.local.dao.UserDao
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userDao: UserDao) : UserRepo {
    override suspend fun updateCreatUserAccount(userEntity: UserEntity) {
        if (userEntity.email.isNotBlank() && userEntity.password.isNotBlank()){
            userDao.updateCreateUserAccount(userEntity)
        }
    }

    override suspend fun loginUserAccount(
        email: String,
        password: String
    ): UserEntity? {
        if (email.isBlank() || password.isBlank()){  // if email or password is blank it will not query the database
            return null
        }
        return userDao.loginUserAccount(email = email, password = password)
    }
}