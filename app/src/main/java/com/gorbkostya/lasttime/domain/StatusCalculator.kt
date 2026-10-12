package com.gorbkostya.lasttime.domain

import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object StatusCalculator {

    fun daysSince(lastDone: LocalDate, today: LocalDate): Long {
        return ChronoUnit.DAYS.between(lastDone, today)
    }


    fun ratio(task: Task, today: LocalDate): Double? {
        if (task.intervalDays == null) {
            return null
        }

        return daysSince(task.lastDone, today).toDouble() / task.intervalDays.toDouble()
    }


    fun status(task: Task, today: LocalDate): TaskStatus {
        val r = ratio(task, today) ?: return TaskStatus.NO_DEADLINE
        return when {
            r < 0.75 -> TaskStatus.OK
            r < 1.0 -> TaskStatus.SOON
            else -> TaskStatus.LATE
        }
    }


    fun progress(task: Task, today: LocalDate): Float {
        val r = ratio(task, today) ?: return 0f
        return r.toFloat().coerceAtMost(1f)
    }
}