package com.example.kidcreds.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kidcreds.data.local.table.ChoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoreDao {

    @Query("SELECT * FROM chores_table ORDER BY isCompleted ASC")
    fun getAllChores(): Flow<List<ChoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChore(chore: ChoreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChores(chores: List<ChoreEntity>)

    @Update
    suspend fun updateChore(chore: ChoreEntity)

    @Delete
    suspend fun deleteChore(chore: ChoreEntity)

    @Query("DELETE FROM chores_table")
    suspend fun deleteAllChores()

}
