package com.gorbkostya.lasttime.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorbkostya.lasttime.data.local.DoneEntity
import com.gorbkostya.lasttime.data.local.TaskDao
import com.gorbkostya.lasttime.data.local.TaskEntity

@Database(
    entities = [TaskEntity::class, DoneEntity::class],
    version = 1
)


abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}