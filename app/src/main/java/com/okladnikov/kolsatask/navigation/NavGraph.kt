package com.okladnikov.kolsatask.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.okladnikov.kolsatask.MainActivity

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    activity: MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = TrainingsScreen.route,
        enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
        exitTransition = { slideOutHorizontally(animationSpec = tween(500)) }
    ) {
        composable(route = TrainingsScreen.route) {
            TODO("not implemented")
        }

        composable(route = TrainingScreen.route) {
            TODO("not implemented")
        }
    }
}
