package com.gorbkostya.lasttime.presentation.edit

import com.gorbkostya.lasttime.domain.model.Category

data class NewTaskState(
    val title: String = "",
    val category: Category = Category.HOME,
    val frequency: Frequency = Frequency.MONTH,
    val lastDone: LastDoneOption = LastDoneOption.TODAY,
    val remind: Boolean = true,
    val isSaved: Boolean = false
) {

    val canSave: Boolean
        get() = title.isNotBlank()
}
