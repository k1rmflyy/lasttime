package com.gorbkostya.lasttime.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.presentation.color
import com.gorbkostya.lasttime.presentation.intervalLabel
import com.gorbkostya.lasttime.presentation.label
import com.gorbkostya.lasttime.presentation.list.previewTasks
import com.gorbkostya.lasttime.presentation.list.previewToday
import com.gorbkostya.lasttime.presentation.statusText
import com.gorbkostya.lasttime.presentation.tint
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme
import com.gorbkostya.lasttime.presentation.ui.theme.NumberLarge
import com.gorbkostya.lasttime.presentation.ui.theme.SpaceGrotesk
import com.gorbkostya.lasttime.presentation.ui.theme.extra
import java.time.LocalDate

@Composable
fun TaskCard(
    task: Task,
    today: LocalDate,
    onDone: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val days = StatusCalculator.daysSince(task.lastDone, today)
    val status = StatusCalculator.status(task, today)
    val progress = StatusCalculator.progress(task, today)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "${task.category.label()} · ${intervalLabel(task.intervalDays)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (days == 0L) {
                Text(
                    text = "Сегодня",
                    fontFamily = SpaceGrotesk,
                    fontSize = 20.sp,
                    color = MaterialTheme.extra.ok
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = days.toString(),
                        style = NumberLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val interval = task.intervalDays ?: 0
                    val over = days - interval

                    Text(
                        text = "${pluralStringResource(R.plurals.days, days.toInt())} назад",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }


        ProgressBar(progress, status.color(), 6.dp)


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusPill(text = statusText(task, today), color = status.color(), tint = status.tint())

            Row(
                modifier = Modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onDone)
                    .padding(start = 10.dp, end = 14.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_check_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Сделано",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}



@Preview(heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskCardDarkPreview() {
    LastTimeTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            previewTasks.forEach { task ->
                TaskCard(task, previewToday, onDone = {}, onClick = {})
            }
        }
    }
}