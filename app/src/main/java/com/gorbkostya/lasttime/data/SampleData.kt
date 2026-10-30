package com.gorbkostya.lasttime.data

import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import java.time.LocalDate

fun sampleTasks(today: LocalDate): List<Task> = listOf(
    Task(0, "Подстричься", Category.OTHER, 90, today.minusDays(24), false),
    Task(0, "Позвонить родным", Category.PEOPLE, 14, today.minusDays(16), true),

)