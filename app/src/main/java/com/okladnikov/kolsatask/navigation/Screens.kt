package com.okladnikov.kolsatask.navigation

sealed class Screen(val route: String)

data object WorkoutsScreen : Screen("workouts_screen")
data object WorkoutScreen : Screen("workout_screen")
