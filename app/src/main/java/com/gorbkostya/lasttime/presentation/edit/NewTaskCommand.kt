package com.gorbkostya.lasttime.presentation.edit

import com.gorbkostya.lasttime.domain.model.Category

sealed interface NewTaskCommand {
    data class TitleChanged(val title: String): NewTaskCommand
    data class CategorySelected(val category: Category): NewTaskCommand
    data class FrequencySelected(val frequency: Frequency): NewTaskCommand
    data class LastDoneSelected(val option: LastDoneOption) : NewTaskCommand
    data object RemindToggled: NewTaskCommand
    data object Save: NewTaskCommand
}