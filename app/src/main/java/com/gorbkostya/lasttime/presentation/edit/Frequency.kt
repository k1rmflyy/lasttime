package com.gorbkostya.lasttime.presentation.edit

enum class Frequency(val days: Int?, val label: String) {
    NONE(null, "Без срока"),
    WEEK(7, "Неделя"),
    TWO_WEEKS(14, "2 недели"),
    MONTH(30, "Месяц"),
    THREE_MONTHS(90, "3 месяца"),
    YEAR(365, "Год")
}