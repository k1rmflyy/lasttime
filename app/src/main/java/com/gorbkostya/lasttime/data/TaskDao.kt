package com.gorbkostya.lasttime.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun observeAll(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Query("UPDATE tasks SET lastDone = :date WHERE id = :id")
    suspend fun updateLastDone(id: Long, date: Long)

    @Insert
    suspend fun insertDone(entry: DoneEntity)

    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun count(): Int

    @Insert
    suspend fun insertAll(task: List<TaskEntity>)


    @Transaction
    suspend fun markDone(id: Long, date: Long) {
        updateLastDone(id, date)
        insertDone(DoneEntity(taskId = id, date = date))
    }

}