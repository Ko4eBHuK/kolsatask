package com.okladnikov.kolsatask.section.workout.viewmodel

import com.okladnikov.kolsatask.domain.Workout

sealed class WorkoutScreenIntent {
    data object CloseError: WorkoutScreenIntent()
    data object LoadVideoInfo: WorkoutScreenIntent()
    data class SetWorkout(val workout: Workout): WorkoutScreenIntent()
    data object PausePlayer: WorkoutScreenIntent()
    data object StartPlayer: WorkoutScreenIntent()
    data object SavePlayerState: WorkoutScreenIntent()
    data object StopPlayer: WorkoutScreenIntent()
    data class SeekPlayer(val seekTime: Long): WorkoutScreenIntent()
}
