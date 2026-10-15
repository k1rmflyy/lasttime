package com.gorbkostya.lasttime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val intervalDays: Int?,
    val lastDone: Long,
    val remind: Boolean
)
