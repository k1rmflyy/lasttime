package com.gorbkostya.lasttime.presentation.list

import androidx.lifecycle.ViewModel
import com.gorbkostya.lasttime.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListViewModel: ViewModel() {

    private val _state = MutableStateFlow(
        ListState(tasks = previewTasks, today = previewToday)
    )

    val state
        get() = _state.asStateFlow()

    fun onCommand(command: ListCommand) {
        when(command) {
            is ListCommand.MarkDone -> {
                _state.update { state ->
                    state.copy(
                        tasks = state.tasks.map { task ->
                            if (task.id == command.taskId) {
                                task.copy(lastDone = previewToday)
                            } else {
                                task
                            }
                        }
                    )
                }
            }
            is ListCommand.SelectCategory -> {
                _state.update { it.copy(selectedCategory = command.category) }
            }
        }
    }
}