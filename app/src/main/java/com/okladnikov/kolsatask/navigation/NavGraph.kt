package com.okladnikov.kolsatask.navigation

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import androidx.navigation.toRoute
import com.okladnikov.kolsatask.MainActivity
import com.okladnikov.kolsatask.R
import com.okladnikov.kolsatask.domain.Workout
import com.okladnikov.kolsatask.section.workout.WorkoutFragment
import com.okladnikov.kolsatask.section.workout.WorkoutScreen
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutScreenIntent
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutViewModel
import com.okladnikov.kolsatask.section.workouts.WorkoutsFragment
import com.okladnikov.kolsatask.section.workouts.WorkoutsScreen
import com.okladnikov.kolsatask.section.workouts.viewmodel.WorkoutsViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    activity: MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = WorkoutsScreen.route,
        enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
        exitTransition = { slideOutHorizontally(animationSpec = tween(500)) }
    ) {
        composable(route = WorkoutsScreen.route) {
            val workoutsViewModel: WorkoutsViewModel by activity.viewModels()
            WorkoutsScreen(
                navController = navController,
                viewModel = workoutsViewModel
            )
        }

        composable<Workout> { backStackEntry ->
            val workout: Workout = backStackEntry.toRoute()
            val workoutViewModel: WorkoutViewModel by activity.viewModels()
            workoutViewModel.processIntent(WorkoutScreenIntent.SetWorkout(workout))
            WorkoutScreen(
                navController = navController,
                viewModel = workoutViewModel
            )
        }
    }
}

fun NavController.initWorkoutGraph(context: Context) {
    graph = createGraph(
        startDestination = WorkoutsScreen.route
    ) {
        fragment<WorkoutsFragment>(route = WorkoutsScreen.route) {
            label = context.getString(R.string.workouts_screen_title)
        }
        fragment<WorkoutFragment, Workout> {
            label = context.getString(R.string.workout_screen_title)
        }
    }
}
