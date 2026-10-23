package com.gorbkostya.lasttime.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class DetailRoute(val taskId: Long)

@Serializable
object NewTaskRoute