package com.gorbkostya.lasttime.presentation.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import com.gorbkostya.lasttime.presentation.components.CategoryChip
import com.gorbkostya.lasttime.presentation.components.StatTile
import com.gorbkostya.lasttime.presentation.components.TaskCard
import com.gorbkostya.lasttime.presentation.label
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme
import com.gorbkostya.lasttime.presentation.ui.theme.extra
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ListScreen(tasks: List<Task>, today: LocalDate) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val lateCount = tasks.count { StatusCalculator.status(it, today) == TaskStatus.LATE }
    val soonCount = tasks.count { StatusCalculator.status(it, today) == TaskStatus.SOON }
    val okCount = tasks.count { StatusCalculator.status(it, today) == TaskStatus.OK }

    val visible = tasks
        .filter { selectedCategory == null || it.category == selectedCategory }
        .sortedByDescending { StatusCalculator.ratio(it, today) ?: -1.0 }

    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.forLanguageTag("ru"))
    val dateText = today.format(formatter).replaceFirstChar { it.uppercase() }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = RoundedCornerShape(22.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(64.dp),

                ) {
                Icon(
                    painterResource(R.drawable.ic_plus),
                    contentDescription = "Новое дело"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = dateText,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "Последний раз",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }



                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable{},
                        contentAlignment = Alignment.Center

                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Поиск",
                            modifier = Modifier.size(22.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatTile(
                       lateCount,
                       "Просрочено",
                       MaterialTheme.extra.late,
                       modifier = Modifier.weight(1f)
                   )

                    StatTile(
                        soonCount,
                        "Скоро",
                        MaterialTheme.extra.soon,
                        modifier = Modifier.weight(1f)
                    )

                    StatTile(
                        okCount,
                        "В порядке",
                        MaterialTheme.extra.ok,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {

                item {
                    CategoryChip("Все", selectedCategory == null) { selectedCategory = null}
                }

                items(Category.entries) { category ->
                    CategoryChip(category.label(), selectedCategory == category) {
                        selectedCategory = category
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "СНАЧАЛА НОВЫЕ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                items(visible, key = {it.id}) { task ->
                    TaskCard(task, today, onDone = {}, onClick = {})
                }
            }
        }
    }
}




@Preview(showSystemUi = true)
@Composable
private fun ListScreenPreview() {
    LastTimeTheme {
        ListScreen(tasks = previewTasks, today = previewToday)
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListScreenDarkPreview() {
    LastTimeTheme(darkTheme = true) {
        ListScreen(tasks = previewTasks, today = previewToday)
    }
}




