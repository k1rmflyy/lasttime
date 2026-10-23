package com.gorbkostya.lasttime

import com.gorbkostya.lasttime.domain.TaskRepository
import com.gorbkostya.lasttime.domain.model.DoneEntry
import com.gorbkostya.lasttime.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class FakeTaskRepository : TaskRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())


    override fun observeTasks(): Flow<List<Task>> {
        return tasks
    }

    override suspend fun markDone(taskId: Long, date: LocalDate) {
        tasks.update {list -> list.map { task->
            if (task.id == taskId) {
                task.copy(lastDone = date)
            } else {
                task
            }
        }}
    }

    override suspend fun seedIfEmpty(samples: List<Task>) {
        if (tasks.value.isEmpty()) {
            tasks.value = samples.mapIndexed { index, task -> task.copy(id = index + 1L) }
        }
    }

    override fun observeTask(id: Long): Flow<Task?> {
        return tasks.map { list -> list.find { it.id == id } }
    }

    override fun observeHistory(taskId: Long): Flow<List<DoneEntry>> {
        return flowOf(emptyList())
    }
}