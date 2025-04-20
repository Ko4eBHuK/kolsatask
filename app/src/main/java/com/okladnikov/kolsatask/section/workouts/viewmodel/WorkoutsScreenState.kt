package com.okladnikov.kolsatask.section.workouts.viewmodel

import com.okladnikov.kolsatask.domain.Workout

data class WorkoutsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val workouts: List<Workout> = listOf()
)
