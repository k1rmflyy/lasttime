package com.gorbkostya.lasttime.domain

import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object StatusCalculator {

    fun daysSince(lastDone: LocalDate, today: LocalDate): Long {
        return ChronoUnit.DAYS.between(lastDone, today)
    }


    fun status(task: Task, today: LocalDate): TaskStatus {
        if (task.intervalDays == null) {
            return TaskStatus.NO_DEADLINE
        }

        val fraction = (daysSince(task.lastDone, today).toDouble() / task.intervalDays.toDouble())
        return when {
            fraction < 0.75 -> TaskStatus.OK
            fraction < 1.0 -> TaskStatus.SOON
            else -> TaskStatus.LATE
        }
    }


    fun progress(task: Task, today: LocalDate): Float {
        if (task.intervalDays == null) {
            return 0f
        }

        val fraction = (daysSince(task.lastDone, today).toFloat() / task.intervalDays.toFloat()).coerceAtMost(1f)
        return fraction
    }
}