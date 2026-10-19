package com.gorbkostya.lasttime.domain

import com.gorbkostya.lasttime.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun markDone(taskId: Long, date: LocalDate)
    suspend fun seedIfEmpty(samples: List<Task>)
}