package com.gorbkostya.lasttime.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gorbkostya.lasttime.presentation.detail.DetailScreen
import com.gorbkostya.lasttime.presentation.edit.NewTaskScreen
import com.gorbkostya.lasttime.presentation.list.ListScreen


private const val DURATION = 650

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
            startDestination = ListRoute,
            enterTransition = {
                slideIntoContainer(SlideDirection.Start, tween(DURATION))
            },
            exitTransition = {
                slideOutOfContainer(SlideDirection.Start, tween(DURATION) {it / 4})
            },
            popEnterTransition = {
                slideIntoContainer(SlideDirection.End, tween(DURATION) {it / 4 })
            },
            popExitTransition = {
                slideOutOfContainer(SlideDirection.End, tween(DURATION))
            }
        ) {
            composable<ListRoute>(
                exitTransition = {
                    if (targetState.destination.hasRoute<NewTaskRoute>()) {
                        fadeOut(tween(DURATION), targetAlpha = 0.9f)
                    } else {
                        slideOutOfContainer(SlideDirection.Start, tween(DURATION)) {it / 4}
                    }
                },
                popEnterTransition = {
                    if (initialState.destination.hasRoute<NewTaskRoute>()) {
                        fadeIn(tween(DURATION), initialAlpha = 0.9f)
                    } else {
                        slideIntoContainer(SlideDirection.End, tween(DURATION)) { it / 4 }
                    }
                }
            ) {

                ListScreen(
                    onTaskClick = {taskId -> navController.navigate(DetailRoute(taskId))},
                    onAddClick = {navController.navigate(NewTaskRoute)}
                )
            }

            composable<DetailRoute> {
                DetailScreen(onBack = {navController.popBackStack()})
            }

            composable<NewTaskRoute>(
                enterTransition = { slideInVertically(tween(DURATION)) {it} },
                popExitTransition = { slideOutVertically(tween(DURATION)) {it} },
            ) {
                NewTaskScreen(onClose = {navController.popBackStack()})
            }
        }
    }

}