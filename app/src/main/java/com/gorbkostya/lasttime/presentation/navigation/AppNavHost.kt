package com.gorbkostya.lasttime.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gorbkostya.lasttime.presentation.detail.DetailScreen
import com.gorbkostya.lasttime.presentation.edit.NewTaskScreen
import com.gorbkostya.lasttime.presentation.list.ListScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navController,
            startDestination = ListRoute
        ) {
            composable<ListRoute> {
                ListScreen(
                    onTaskClick = {taskId -> navController.navigate(DetailRoute(taskId))},
                    onAddClick = {navController.navigate(NewTaskRoute)}
                )
            }

            composable<DetailRoute> {
                DetailScreen(onBack = {navController.popBackStack()})
            }

            composable<NewTaskRoute> {
                NewTaskScreen(onClose = {navController.popBackStack()})
            }
        }
    }

}