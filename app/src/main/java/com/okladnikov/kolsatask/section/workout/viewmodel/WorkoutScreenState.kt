package com.okladnikov.kolsatask.section.workout.viewmodel

import androidx.media3.exoplayer.ExoPlayer
import com.okladnikov.kolsatask.domain.VideoWorkout
import com.okladnikov.kolsatask.domain.Workout

data class WorkoutScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val workout: Workout? = null,
    val videoWorkout: VideoWorkout? = null,
    val exoPlayer: ExoPlayer
)
