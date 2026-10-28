package com.gorbkostya.lasttime.presentation.list

import com.gorbkostya.lasttime.domain.model.Category

sealed interface ListCommand {
    data class SelectCategory(val category: Category?): ListCommand
    data class MarkDone(val taskId: Long): ListCommand

    data object SearchOpened: ListCommand

    data object SearchClosed: ListCommand

    data class QueryChanged(val query: String): ListCommand
}