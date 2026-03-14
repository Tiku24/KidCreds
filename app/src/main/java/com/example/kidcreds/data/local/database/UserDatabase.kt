package com.example.kidcreds.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kidcreds.data.local.dao.UserDao
import com.example.kidcreds.data.local.table.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}