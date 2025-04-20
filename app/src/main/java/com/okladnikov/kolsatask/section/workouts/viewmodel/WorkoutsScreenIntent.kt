package com.okladnikov.kolsatask.section.workouts.viewmodel

import com.okladnikov.kolsatask.domain.Workout

sealed class WorkoutsScreenIntent {
    data class OpenWorkout(val workout: Workout): WorkoutsScreenIntent()
    data object Refresh: WorkoutsScreenIntent()
    data object CloseError: WorkoutsScreenIntent()
}
