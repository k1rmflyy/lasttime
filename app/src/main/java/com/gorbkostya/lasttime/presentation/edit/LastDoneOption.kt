package com.gorbkostya.lasttime.presentation.edit

enum class LastDoneOption(val daysAgo: Long, val label: String) {
    TODAY(0, "Сегодня"),
    YESTERDAY(1, "Вчера"),
    WEEK_AGO(7, "Неделю назад")
}

