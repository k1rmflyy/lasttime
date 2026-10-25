package com.gorbkostya.lasttime.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gorbkostya.lasttime.domain.TaskRepository
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.presentation.LastTimeApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewTaskViewModel(
    private val repository: TaskRepository
): ViewModel() {

    private val _state = MutableStateFlow(NewTaskState())
    val state
        get() = _state.asStateFlow()

    fun onCommand(command: NewTaskCommand) {
        when(command) {
            is NewTaskCommand.CategorySelected -> _state.update { it.copy(category = command.category) }
            is NewTaskCommand.FrequencySelected -> _state.update { it.copy(frequency = command.frequency) }
            is NewTaskCommand.LastDoneSelected -> _state.update { it.copy(lastDone = command.option) }
            NewTaskCommand.RemindToggled -> _state.update { it.copy(remind = !it.remind) }
            NewTaskCommand.Save -> save()
            is NewTaskCommand.TitleChanged -> _state.update { it.copy(title = command.title) }
        }
    }

    private fun save() {
        val s = _state.value

        if (!s.canSave) return

        val task = Task(
            id = 0,
            title = s.title.trim(),
            category = s.category,
            intervalDays = s.frequency.days,
            lastDone = LocalDate.now().minusDays(s.lastDone.daysAgo),
            remind = s.remind
        )

        viewModelScope.launch {
            repository.addTask(task)
            _state.update { it.copy(isSaved = true) }
        }


    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LastTimeApp
                NewTaskViewModel(app.container.taskRepository)
            }
        }
    }
}