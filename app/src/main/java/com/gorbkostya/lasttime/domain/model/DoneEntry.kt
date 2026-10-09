package com.gorbkostya.lasttime.domain.model

import java.time.LocalDate

data class DoneEntry(
    val id: Long,
    val taskId: Long,
    val date: LocalDate
)
