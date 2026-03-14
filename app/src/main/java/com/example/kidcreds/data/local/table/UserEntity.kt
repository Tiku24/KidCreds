package com.example.kidcreds.data.local.table

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avatar: String? = null,
    val name: String,
    val email: String,
    val password: String
)
