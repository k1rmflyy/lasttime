package com.gorbkostya.lasttime.data.local

import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.DoneEntry
import com.gorbkostya.lasttime.domain.model.Task
import java.time.LocalDate

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    category = Category.valueOf(category),
    intervalDays = intervalDays,
    lastDone = LocalDate.ofEpochDay(lastDone),
    remind = remind
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    category = category.name,
    intervalDays = intervalDays,
    lastDone = lastDone.toEpochDay(),
    remind = remind
)

fun DoneEntity.toDomain(): DoneEntry = DoneEntry(
    id = id,
    taskId = taskId,
    date = LocalDate.ofEpochDay(date)
)