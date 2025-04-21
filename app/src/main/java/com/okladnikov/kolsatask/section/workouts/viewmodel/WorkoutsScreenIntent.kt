package com.okladnikov.kolsatask.section.workouts.viewmodel

sealed class WorkoutsScreenIntent {
    data object Refresh: WorkoutsScreenIntent()
    data object CloseError: WorkoutsScreenIntent()
}
