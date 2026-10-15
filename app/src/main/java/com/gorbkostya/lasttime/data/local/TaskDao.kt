package com.gorbkostya.lasttime.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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
}