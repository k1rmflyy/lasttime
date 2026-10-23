package com.gorbkostya.lasttime.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gorbkostya.lasttime.data.sampleTasks
import com.gorbkostya.lasttime.domain.TaskRepository
import com.gorbkostya.lasttime.presentation.LastTimeApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ListViewModel(
    private val repository: TaskRepository
): ViewModel() {

    private val _state = MutableStateFlow(
        ListState(today = LocalDate.now())
    )

    val state
        get() = _state.asStateFlow()


    init {
        viewModelScope.launch {
            repository.seedIfEmpty(sampleTasks(LocalDate.now()))
        }

        viewModelScope.launch {
            repository.observeTasks().collect { tasks ->
                _state.update { it.copy(tasks = tasks) }
            }
        }
    }


    fun onCommand(command: ListCommand) {
        when(command) {
            is ListCommand.MarkDone -> {
                viewModelScope.launch {
                    repository.markDone(command.taskId, _state.value.today)
                }
            }
            is ListCommand.SelectCategory -> {
                _state.update { it.copy(selectedCategory = command.category) }
            }
        }
    }



    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LastTimeApp
                ListViewModel(app.container.taskRepository)
            }
        }
    }
}