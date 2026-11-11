package com.example.kidcreds.data.repositoryimpl

import android.util.Log
import com.example.kidcreds.data.local.dao.ChoreDao
import com.example.kidcreds.data.local.table.ChoreEntity
import com.example.kidcreds.data.local.table.ChoreFrequency
import com.example.kidcreds.data.local.table.ChorePriority
import com.example.kidcreds.data.repository.ChoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChoreRepoImpl @Inject constructor(private val choreDao: ChoreDao): ChoreRepo {

    override fun getChores(): Flow<List<ChoreEntity>> = choreDao.getAllChores()

    override suspend fun addChore(chore: List<ChoreEntity>) = choreDao.insertChores(chores = chore)
    override suspend fun insertChore(chore: ChoreEntity) = choreDao.insertChore(chore)

    override suspend fun toggleChoreStatus(chore: ChoreEntity) = choreDao.updateChore(chore)

    override suspend fun deleteChore(chore: ChoreEntity) = choreDao.deleteChore(chore)
    override suspend fun choreDummyData() {
        choreDao.deleteAllChores()
        val choreDummyList = listOf(
            ChoreEntity(
                title = "Brush Teeth",
                description = "Morning and Night",
                points = 2,
                priority = ChorePriority.MUST,
                frequency = ChoreFrequency.DAILY,
                assignedTo = "Rahul"
            ),
            ChoreEntity(
                title = "Dog Walk",
                description = "Optional weekend task",
                points = 5,
                priority = ChorePriority.OPTIONAL,
                frequency = ChoreFrequency.AS_NEEDED,
                assignedTo = "Mark"
            ),
            ChoreEntity(
                title = "Wash Car",
                description = "Optional weekend task",
                points = 50,
                priority = ChorePriority.OPTIONAL,
                frequency = ChoreFrequency.AS_NEEDED,
                assignedTo = "Marcus"
            ),
            ChoreEntity(
                title = "Homework",
                description = "Complete all school tasks",
                points = 20,
                priority = ChorePriority.MUST,
                frequency = ChoreFrequency.DAILY,
                assignedTo = "Ving"
            ),
            ChoreEntity(
                title = "Make bed",
                description = "Complete tasks",
                points = 20,
                priority = ChorePriority.MUST,
                frequency = ChoreFrequency.DAILY,
                assignedTo = "John"
            ),
            ChoreEntity(
                title = "Go on shop",
                description = "Buy groceries",
                points = 20,
                priority = ChorePriority.MUST,
                frequency = ChoreFrequency.DAILY,
                assignedTo = "Rio"
            )
        )
        choreDao.insertChores(choreDummyList)
        Log.d("DB_DEBUG", "Count = ${choreDummyList.size}")
    }
}
