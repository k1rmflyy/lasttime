package com.gorbkostya.lasttime.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.gorbkostya.lasttime.domain.TaskRepository
import com.gorbkostya.lasttime.presentation.LastTimeApp
import com.gorbkostya.lasttime.presentation.navigation.DetailRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val taskId: Long,
    private val repository: TaskRepository
): ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.observeTask(taskId),
                repository.observeHistory(taskId)
            ) {task, history ->
                task to history
            }.collect { (task, history) ->
                _state.update { it.copy(task = task, history = history) }
            }
        }
    }


    fun onCommand(command: DetailCommand) {
        when(command) {
            DetailCommand.MarkDone -> {
                viewModelScope.launch {
                    repository.markDone(taskId, _state.value.today)
                }
            }

            DetailCommand.RemindToggled -> {
                val cur = _state.value.task?.remind ?: return
                viewModelScope.launch {
                    repository.setRemind(taskId, !cur)
                }
            }

            DetailCommand.DeleteClicked -> {
                _state.update { it.copy(showDeleteDialog = true) }
            }

            DetailCommand.DeleteConfirmed -> {
                viewModelScope.launch {
                    repository.deleteTask(taskId)
                    _state.update { it.copy(showDeleteDialog = false, isDeleted = true) }
                }
            }

            DetailCommand.DeleteDismissed -> {
                _state.update { it.copy(showDeleteDialog = false) }
            }
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LastTimeApp
                val route = createSavedStateHandle().toRoute<DetailRoute>()
                DetailViewModel(route.taskId, app.container.taskRepository)
            }
        }
    }
}