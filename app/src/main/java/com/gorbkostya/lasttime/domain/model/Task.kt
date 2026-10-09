package com.gorbkostya.lasttime.domain.model

import java.time.LocalDate

data class Task(
    val id: Long,
    val title: String,
    val category: Category,
    val intervalDays: Int?,
    val lastDone: LocalDate,
    val remind: Boolean
)