package com.gorbkostya.lasttime.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gorbkostya.lasttime.presentation.LastTimeApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class DoneReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(ReminderNotifier.EXTRA_TASK_ID, -1L)
        if (taskId == 1L) return

        val pendingResult = goAsync()
        val app = context.applicationContext as LastTimeApp

        CoroutineScope(Dispatchers.IO).launch {
            try {
                app.container.taskRepository.markDone(taskId, LocalDate.now())
                ReminderNotifier.cancel(context, taskId)
            } finally {
                pendingResult.finish()
            }
        }
    }
}