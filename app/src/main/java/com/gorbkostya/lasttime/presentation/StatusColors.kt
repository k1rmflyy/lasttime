package com.gorbkostya.lasttime.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gorbkostya.lasttime.domain.model.TaskStatus
import com.gorbkostya.lasttime.presentation.ui.theme.extra

@Composable
fun TaskStatus.color(): Color = when(this) {
        TaskStatus.OK -> MaterialTheme.extra.ok
        TaskStatus.SOON -> MaterialTheme.extra.soon
        TaskStatus.LATE -> MaterialTheme.extra.late
        TaskStatus.NO_DEADLINE -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Composable
fun TaskStatus.tint(): Color = when(this) {
        TaskStatus.OK -> MaterialTheme.extra.okTint
        TaskStatus.SOON -> MaterialTheme.extra.soonTint
        TaskStatus.LATE -> MaterialTheme.extra.lateTint
        TaskStatus.NO_DEADLINE -> MaterialTheme.colorScheme.surfaceVariant
}