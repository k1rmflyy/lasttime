package com.gorbkostya.lasttime.domain

import com.gorbkostya.lasttime.domain.model.DoneEntry
import com.gorbkostya.lasttime.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun markDone(taskId: Long, date: LocalDate)
    suspend fun seedIfEmpty(samples: List<Task>)

    fun observeTask(id: Long): Flow<Task?>

    fun observeHistory(taskId: Long): Flow<List<DoneEntry>>

    suspend fun addTask(task: Task): Long

    suspend fun setRemind(taskId: Long, remind: Boolean)

    suspend fun deleteTask(id: Long)
}