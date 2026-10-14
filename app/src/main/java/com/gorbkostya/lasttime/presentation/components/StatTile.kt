package com.gorbkostya.lasttime.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gorbkostya.lasttime.domain.model.TaskStatus
import com.gorbkostya.lasttime.presentation.color
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme
import com.gorbkostya.lasttime.presentation.ui.theme.NumberLarge

@Composable
fun StatTile(
    count: Int,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        Text(
            text = count.toString(),
            style = NumberLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}



@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryChipPreview() {
    LastTimeTheme(darkTheme = true) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            StatTile(2, "Просрочено", TaskStatus.LATE.color())
            StatTile(2, "Cкоро", TaskStatus.SOON.color())
            StatTile(3, "В порядке", TaskStatus.OK.color())
        }
    }
}