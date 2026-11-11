package com.example.kidcreds.data.repository

import com.example.kidcreds.data.local.table.ChoreEntity
import kotlinx.coroutines.flow.Flow

interface ChoreRepo {
    fun getChores(): Flow<List<ChoreEntity>>
    suspend fun addChore(chore: List<ChoreEntity>)
    suspend fun insertChore(chore: ChoreEntity)
    suspend fun toggleChoreStatus(chore: ChoreEntity)
    suspend fun deleteChore(chore: ChoreEntity)
    suspend fun choreDummyData()
}