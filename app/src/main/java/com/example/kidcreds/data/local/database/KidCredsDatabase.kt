package com.example.kidcreds.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kidcreds.data.local.dao.ChoreDao
import com.example.kidcreds.data.local.dao.UserDao
import com.example.kidcreds.data.local.table.ChoreEntity
import com.example.kidcreds.data.local.table.UserEntity

@Database(entities = [UserEntity::class, ChoreEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KidCredsDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun choreDao(): ChoreDao
}