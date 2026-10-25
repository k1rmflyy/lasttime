package com.gorbkostya.lasttime.data

import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import java.time.LocalDate

fun sampleTasks(today: LocalDate): List<Task> = listOf(
    Task(0, "Поменять зубную щётку", Category.HEALTH, 90, today.minusDays(97), true),
    Task(0, "Позвонить бабушке", Category.PEOPLE, 14, today.minusDays(16), true),
    Task(0, "Полить цветы", Category.HOME, 7, today.minusDays(6), false),
    Task(0, "Стрижка", Category.HEALTH, 30, today.minusDays(24), false),
    Task(0, "Заменить фильтр в кувшине", Category.HOME, 60, today.minusDays(38), true),
    Task(0, "Сменить постельное бельё", Category.HOME, 14, today.minusDays(5), false),
    Task(0, "Сделать Д/З", Category.EDUCATION, 365, today.minusDays(120), true),
    Task(0, "Выполнить задачу в срок", Category.WORK, 7, today.minusDays(5), false),
)