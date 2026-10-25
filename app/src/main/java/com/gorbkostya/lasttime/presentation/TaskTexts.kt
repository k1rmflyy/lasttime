package com.gorbkostya.lasttime.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import java.time.LocalDate

fun Category.label(): String  {
    return when(this) {
        Category.HOME -> "Дом"
        Category.HEALTH -> "Здоровье"
        Category.PEOPLE -> "Люди"
        Category.EDUCATION -> "Образование"
        Category.WORK -> "Работа"
        Category.OTHER -> "Другое"
    }
}


fun intervalLabel(days: Int?): String {
    return when(days) {
        null -> "Без срока"
        7 -> "Раз в неделю"
        14 -> "Раз в 2 недели"
        30 -> "Раз в месяц"
        90 -> "Раз в 3 месяца"
        365 -> "Раз в год"
        else -> "Раз в $days дней"
    }
}



@Composable
fun statusText(task: Task, today: LocalDate): String {
    val days = StatusCalculator.daysSince(task.lastDone, today).toInt()
    val status = StatusCalculator.status(task, today)
    val interval = task.intervalDays ?: 0


    return when {
        days == 0 -> "Сделано сегодня"
        status == TaskStatus.NO_DEADLINE -> "Без срока"
        days == interval -> "Пора сегодня"
        status == TaskStatus.LATE -> {
            val over = days - interval
            "Просрочено на $over ${pluralStringResource(R.plurals.days, over)}"
        }
        status == TaskStatus.SOON -> {
            val left = interval - days
            "Осталось $left ${pluralStringResource(R.plurals.days, left)}"
        }
        else -> "В порядке"
    }
}
