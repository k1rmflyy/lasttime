package com.gorbkostya.lasttime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TaskEntity::class, DoneEntity::class],
    version = 1
)


abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}