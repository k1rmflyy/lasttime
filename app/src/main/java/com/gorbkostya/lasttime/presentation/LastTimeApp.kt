package com.gorbkostya.lasttime.presentation

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.gorbkostya.lasttime.data.AppContainer
import com.gorbkostya.lasttime.notifications.ReminderNotifier
import com.gorbkostya.lasttime.notifications.ReminderWorker
import java.util.concurrent.TimeUnit

class LastTimeApp: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        ReminderNotifier.createChannel(this)
        scheduleReminders()
    }


    private fun scheduleReminders() {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            uniqueWorkName = "daily_reminders",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = request
        )
    }
}