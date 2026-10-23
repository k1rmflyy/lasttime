package com.gorbkostya.lasttime.presentation.detail

sealed interface DetailCommand {
    data object MarkDone: DetailCommand
}