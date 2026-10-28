package com.gorbkostya.lasttime.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.presentation.LastTimeApp
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as LastTimeApp
        val repository = app.container.taskRepository
        val today = LocalDate.now()


        val tasks = repository.observeTasks().first()

        tasks
            .filter { it.remind && it.intervalDays != null }
            .forEach { task ->
                val interval = task.intervalDays ?: return@forEach
                val passed = StatusCalculator.daysSince(task.lastDone, today).toInt()
                val left = interval - passed


                val text = when {
                    left < 0 -> {
                        val overdue = -left
                        "Просрочено на $overdue ${days(overdue)}"
                    }

                    left == 0 -> {
                        "Пора сегодня"
                    }

                    left <= 3 -> {
                        "Осталось $left ${days((left))}"
                    }

                    else -> null
                }

                if (text != null) {
                    ReminderNotifier.show(applicationContext, task, text)
                }
            }

        return Result.success()
    }


    private fun days(count: Int): String =
        applicationContext.resources.getQuantityString(R.plurals.days, count)
}