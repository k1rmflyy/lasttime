package com.gorbkostya.lasttime.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.gorbkostya.lasttime.MainActivity
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.model.Task

object ReminderNotifier {

    const val CHANNEL_ID = "reminders"
    const val EXTRA_TASK_ID = "task_id"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Напоминания",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Напоминания о делах, которые пора повторить"
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    fun show(context: Context, task: Task, text: String) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_TASK_ID, task.id)
        }

        val pendingOpen = PendingIntent.getActivity(
            context,
            task.id.toInt(),
            openIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val doneIntent = Intent(context, DoneReceiver::class.java).apply {
            putExtra(EXTRA_TASK_ID, task.id)
        }

        val pendingDone = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            doneIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(task.title)
            .setContentText(text)
            .setContentIntent(pendingOpen)
            .addAction(R.drawable.ic_check, "Сделано", pendingDone)
            .setAutoCancel(true)
            .build()


        NotificationManagerCompat.from(context).notify(task.id.toInt(), notification)
    }


    fun cancel(context: Context, taskId: Long) {
        NotificationManagerCompat.from(context).cancel(taskId.toInt())
    }
}