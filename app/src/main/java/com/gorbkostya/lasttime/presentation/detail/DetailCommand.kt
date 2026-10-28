package com.gorbkostya.lasttime.presentation.detail

sealed interface DetailCommand {
    data object MarkDone: DetailCommand
    data object RemindToggled: DetailCommand

    data object DeleteClicked: DetailCommand

    data object DeleteConfirmed: DetailCommand

    data object DeleteDismissed: DetailCommand
}