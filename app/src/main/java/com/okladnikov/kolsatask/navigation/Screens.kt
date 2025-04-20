package com.okladnikov.kolsatask.navigation

sealed class Screen(val route: String)

data object TrainingsScreen : Screen("trainings_screen")
data object TrainingScreen : Screen("training_screen")
