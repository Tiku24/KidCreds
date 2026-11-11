package com.example.kidcreds.data.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ChorePriority { MUST, OPTIONAL }
enum class ChoreFrequency { DAILY, AS_NEEDED }

@Entity(tableName = "chores_table")
data class ChoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val points: Int,
    val isCompleted: Boolean = false,
    val lastCompletedDate: Long? = null,
    val assignedTo: String,
    val priority: ChorePriority = ChorePriority.OPTIONAL,
    val frequency: ChoreFrequency = ChoreFrequency.AS_NEEDED,
    val iconRes: Int? = null // For simplicity, we'll use iconRes or placeholders
)
