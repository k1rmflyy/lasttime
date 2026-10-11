package com.gorbkostya.lasttime.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gorbkostya.lasttime.domain.model.TaskStatus
import com.gorbkostya.lasttime.presentation.color
import com.gorbkostya.lasttime.presentation.tint
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme
import com.gorbkostya.lasttime.presentation.ui.theme.extra


@Composable
fun StatusPill(
    text: String,
    color: Color,
    tint: Color,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(28.dp)
            .background(tint, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        ) { }

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview
@Composable
private fun StatusPillPreview() {
    LastTimeTheme {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusPill("В порядке", TaskStatus.OK.color(), TaskStatus.OK.tint())
            StatusPill(
                "Просрочено на 7 дней",
                TaskStatus.LATE.color(),
                TaskStatus.LATE.tint()
            )
            StatusPill("Осталось 2 дня", TaskStatus.SOON.color(), TaskStatus.SOON.tint())
        }
    }
}