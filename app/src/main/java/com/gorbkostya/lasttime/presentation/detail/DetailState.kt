package com.gorbkostya.lasttime.presentation.detail

import com.gorbkostya.lasttime.domain.model.DoneEntry
import com.gorbkostya.lasttime.domain.model.Task
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class DetailState(
    val task: Task? = null,
    val history: List<DoneEntry> = emptyList(),
    val today: LocalDate = LocalDate.now()
) {

    val doneToday: Boolean
        get() = task?.lastDone == today

    val averageInterval: Int?
        get() {
            if (history.size < 2) return null
            return (ChronoUnit.DAYS.between(history.last().date, history.first().date) / (history.size - 1)).toInt()
        }
}
