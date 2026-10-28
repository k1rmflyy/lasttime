package com.gorbkostya.lasttime.presentation.list

import com.gorbkostya.lasttime.domain.StatusCalculator
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.domain.model.Task
import com.gorbkostya.lasttime.domain.model.TaskStatus
import java.time.LocalDate

data class ListState(
    val tasks: List<Task> = emptyList(),
    val selectedCategory: Category? = null,
    val today: LocalDate = LocalDate.now(),
    val isSearching: Boolean = false,
    val query: String = ""
) {
    val visible: List<Task>
        get() = tasks
            .filter { selectedCategory == null ||  it.category == selectedCategory}
            .filter { query.isBlank() || it.title.contains(query.trim(), ignoreCase = true) }
            .sortedByDescending { StatusCalculator.ratio(it, today) ?: -1.0}

    val lateCount: Int
        get() = tasks
            .count { StatusCalculator.status(it, today) == TaskStatus.LATE }

    val soonCount: Int
        get() = tasks
            .count { StatusCalculator.status(it, today) == TaskStatus.SOON }


    val okCount: Int
        get() = tasks
            .count { StatusCalculator.status(it, today) == TaskStatus.OK }
}
