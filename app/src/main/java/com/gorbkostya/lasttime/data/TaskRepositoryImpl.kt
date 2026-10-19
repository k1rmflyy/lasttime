package com.gorbkostya.lasttime.data

import com.gorbkostya.lasttime.data.local.TaskDao
import com.gorbkostya.lasttime.data.local.toDomain
import com.gorbkostya.lasttime.data.local.toEntity
import com.gorbkostya.lasttime.domain.TaskRepository
import com.gorbkostya.lasttime.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override fun observeTasks(): Flow<List<Task>> {
        return dao.observeAll().map {entities ->
            entities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun markDone(taskId: Long, date: LocalDate) {
        dao.markDone(taskId, date.toEpochDay())
    }

    override suspend fun seedIfEmpty(samples: List<Task>) {
        if (dao.count() == 0) {
            dao.insertAll(samples.map { it.toEntity() })
        }
    }
}