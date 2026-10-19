package com.gorbkostya.lasttime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "done_entries")
data class DoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: Long,
    val date: Long
)
