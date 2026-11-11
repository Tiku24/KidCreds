package com.example.kidcreds.data.local.database

import androidx.room.TypeConverter
import com.example.kidcreds.data.local.table.ChoreFrequency
import com.example.kidcreds.data.local.table.ChorePriority

class Converters {
    @TypeConverter
    fun fromPriority(priority: ChorePriority) = priority.name
    @TypeConverter
    fun toPriority(value: String) = ChorePriority.valueOf(value)

    @TypeConverter
    fun fromFrequency(frequency: ChoreFrequency) = frequency.name

    @TypeConverter
    fun toFrequency(value: String) = ChoreFrequency.valueOf(value)
}