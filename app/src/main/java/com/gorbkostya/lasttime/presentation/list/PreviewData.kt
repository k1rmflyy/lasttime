package com.gorbkostya.lasttime.presentation.list

import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import java.time.LocalDate

val previewToday = LocalDate.of(2026, 10, 5)

val previewTasks = listOf(
    Task(1, "Поменять зубную щётку", Category.HEALTH, 90, previewToday.minusDays(97), true),
    Task(2, "Полить цветы", Category.HOME, 7, previewToday.minusDays(6), false),
    Task(3, "Заменить фильтр в кувшине", Category.HOME, 60, previewToday.minusDays(38), true),
    Task(4, "Сменить постельное бельё", Category.HOME, 14, previewToday.minusDays(0), false),
)