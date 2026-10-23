package com.gorbkostya.lasttime.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gorbkostya.lasttime.presentation.detail.DetailScreen
import com.gorbkostya.lasttime.presentation.list.ListScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

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
            Text(
                text = "Новое дело — скоро"
            )
        }
    }
}