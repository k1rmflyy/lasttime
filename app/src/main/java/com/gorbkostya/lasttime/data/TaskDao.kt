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


    @Query("SELECT * FROM tasks WHERE id = :id")
    fun observeTask(id: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM done_entries WHERE taskId = :taskId ORDER BY date DESC")
    fun observeHistory(taskId: Long): Flow<List<DoneEntity>>

    @Insert
    suspend fun insertWithHistory(task: TaskEntity): Long {
        val id = insert(task)
        insertDone(DoneEntity(taskId = id, date = task.lastDone))
        return id
    }

    @Query("UPDATE tasks SET remind =:remind WHERE id = :id")
    suspend fun updateRemind(remind: Boolean, id: Long)


    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskRow(id: Long)


    @Query("DELETE FROM done_entries WHERE taskId = :taskId")
    suspend fun deleteHistory(taskId: Long)

    @Transaction
    suspend fun deleteTask(id: Long) {
        deleteHistory(id)
        deleteTaskRow(id)
    }
}