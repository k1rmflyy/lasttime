package com.gorbkostya.lasttime.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gorbkostya.lasttime.R
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.presentation.components.CategoryChip
import com.gorbkostya.lasttime.presentation.components.OptionButton
import com.gorbkostya.lasttime.presentation.label

@Composable
fun NewTaskScreen(
    onClose: () -> Unit,
    viewModel: NewTaskViewModel = viewModel(factory = NewTaskViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onClose()
        }
    }

    NewTaskContent(state = state, onCommand = viewModel::onCommand, onClose = onClose)
}

@Composable
fun NewTaskContent(
    state: NewTaskState,
    onCommand: (NewTaskCommand) -> Unit,
    onClose: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {


        Row(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            FloatingActionButton(
                onClick = onClose,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(44.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "Отмена"
                )
            }


            Text(
                text = "Новое дело",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.size(44.dp))
        }




        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Что нужно сделать",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp)
                )


                OutlinedTextField(
                    value = state.title,
                    onValueChange = { onCommand(NewTaskCommand.TitleChanged(it)) },
                    placeholder = { Text("Например, полить цветы") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    textStyle = MaterialTheme.typography.titleMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )


                val suggestions = listOf(
                    "Оплата услуг",
                    "Стрижка",
                    "Чекап здоровья",
                    "Повайбкодить",
                    "Постираться"
                )

                if (state.title.isBlank()) {
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        suggestions.forEach { text ->
                            Box(
                                modifier = Modifier
                                    .height(32.dp)
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.outlineVariant,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable { onCommand(NewTaskCommand.TitleChanged(text)) }
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+ $text",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }




            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Категория",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp)
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Category.entries.forEach { category ->
                        CategoryChip(
                            label = category.label(),
                            selected = state.category == category
                        ) {
                            onCommand(NewTaskCommand.CategorySelected(category))
                        }
                    }
                }
            }





            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {


                Text(
                    text = "Как часто",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp)
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Frequency.entries.chunked(3).forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            row.forEach { frequency ->
                                OptionButton(
                                    label = frequency.label,
                                    selected = state.frequency == frequency,
                                    onClick = { onCommand(NewTaskCommand.FrequencySelected(frequency)) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }




            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {


                Text(
                    text = "Когда было в последний раз",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 4.dp)
                )



                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    LastDoneOption.entries.forEach { lastDoneOption ->
                        CategoryChip(
                            label = lastDoneOption.label,
                            selected = state.lastDone == lastDoneOption
                        ) {
                            onCommand(NewTaskCommand.LastDoneSelected(lastDoneOption))
                        }
                    }
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .toggleable(
                            value = state.remind,
                            role = Role.Switch,
                            onValueChange = { onCommand(NewTaskCommand.RemindToggled) }
                        )
                        .padding(horizontal = 18.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Напомнить, когда пора",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "за 3 дня до срока",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = state.remind,
                        onCheckedChange = null
                    )
                }
            }


        }

        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 32.dp)
        ) {
            Button(
                onClick = {onCommand(NewTaskCommand.Save)},
                enabled = state.canSave,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {

                Text(
                    text = "Сохранить",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}