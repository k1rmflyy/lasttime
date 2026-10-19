package com.gorbkostya.lasttime.data

import android.content.Context
import androidx.room.Room
import com.gorbkostya.lasttime.data.local.AppDatabase

class AppContainer(context: Context) {


    private val database: AppDatabase = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = "lasttime.db"
    ).build()

    val taskRepository = TaskRepositoryImpl(database.taskDao())
}