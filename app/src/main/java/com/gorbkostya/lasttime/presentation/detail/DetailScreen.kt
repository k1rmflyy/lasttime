package com.gorbkostya.lasttime.presentation.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.DoneEntry
import com.gorbkostya.lasttime.presentation.color
import com.gorbkostya.lasttime.presentation.components.ProgressBar
import com.gorbkostya.lasttime.presentation.components.StatusPill
import com.gorbkostya.lasttime.presentation.intervalLabel
import com.gorbkostya.lasttime.presentation.label
import com.gorbkostya.lasttime.presentation.list.previewTasks
import com.gorbkostya.lasttime.presentation.list.previewToday
import com.gorbkostya.lasttime.presentation.statusText
import com.gorbkostya.lasttime.presentation.tint
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme
import com.gorbkostya.lasttime.presentation.ui.theme.NumberHero
import com.gorbkostya.lasttime.presentation.ui.theme.SpaceGrotesk
import com.gorbkostya.lasttime.presentation.ui.theme.extra
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailContent(state = state, onCommand = viewModel::onCommand, onBack = onBack)
}


@Composable
fun DetailContent(
    state: DetailState,
    onCommand: (DetailCommand) -> Unit,
    onBack: () -> Unit
) {

    val task = state.task ?: return
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("ru"))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            FloatingActionButton(
                onClick = onBack,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(44.dp)

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Назад"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = task.category.label(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val days = StatusCalculator.daysSince(task.lastDone, state.today).toInt()
                val status = StatusCalculator.status(task, state.today)

                if (days == 0) {
                    Text(
                        text = "Сегодня",
                        fontFamily = SpaceGrotesk,
                        fontSize = 64.sp,
                        color = MaterialTheme.extra.ok
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "$days",
                            style = NumberHero,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.alignByBaseline()
                        )

                        Text(
                            text = "${pluralStringResource(R.plurals.days, days)} назад",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                }
                val dateText = task.lastDone.format(formatter)

                Text(
                    text = "Последний раз — $dateText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )


                ProgressBar(StatusCalculator.progress(task, state.today), status.color(), 8.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatusPill(statusText(task, state.today), status.color(), status.tint())

                    Text(
                        text = intervalLabel(task.intervalDays),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }


            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "ИСТОРИЯ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )


                val avg = state.averageInterval
                if (avg != null) {
                    Text(
                        text = "в среднем раз в $avg ${pluralStringResource(R.plurals.days, avg)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (state.history.isEmpty()) {
                Text(
                    text = "Пока нет записей",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 4.dp, horizontal = 18.dp)
                ) {


                    state.history.forEachIndexed { index, entry ->
                        if (index > 0) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        }

                        val gapText = if (index == state.history.lastIndex) {
                            "первая запись"
                        } else {
                            val previous = state.history[index + 1]
                            val gap = ChronoUnit.DAYS.between(previous.date, entry.date).toInt()
                            "через $gap ${pluralStringResource(R.plurals.days, gap)}"
                        }

                        val dotColor = if (index == 0) {
                            MaterialTheme.extra.ok
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(dotColor, CircleShape)
                                )

                                Text(
                                    text = entry.date.format(formatter),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = gapText,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }



        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 20.dp)
        ) {
            Button(
                onClick = {onCommand(DetailCommand.MarkDone)},
                enabled = !state.doneToday,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_check_24),
                    contentDescription = null
                )

                Spacer(Modifier.width(10.dp))

                Text(if (state.doneToday) "Уже сделано сегодня" else "Сделано сегодня")
            }
        }
    }

}







@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailContentDarkPreview() {
    LastTimeTheme(darkTheme = true) {
        DetailContent(
            state = DetailState(
                task = previewTasks.first(),
                history = listOf(
                    DoneEntry(id = 3, taskId = 1, date = previewToday.minusDays(97)),
                    DoneEntry(id = 2, taskId = 1, date = previewToday.minusDays(191)),
                    DoneEntry(id = 1, taskId = 1, date = previewToday.minusDays(283)),
                ),
                today = previewToday
            ),
            onCommand = {},
            onBack = {}
        )
    }
}