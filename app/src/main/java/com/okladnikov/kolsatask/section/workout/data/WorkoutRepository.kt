package com.okladnikov.kolsatask.section.workout.data

import com.okladnikov.kolsatask.utils.NetworkCallState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val api: WorkoutApi
) {
    fun getWorkoutVideo(workoutId: Int) = flow {
        emit(NetworkCallState.loading("Загрузка информации о видео"))
        try {
            val workoutVideoResponse = api.getWorkoutVideo(workoutId)
            if (workoutVideoResponse.isSuccessful) {
                if (workoutVideoResponse.body() != null) {
                    workoutVideoResponse.body()?.let { emit(NetworkCallState.success(it)) }
                }
            } else {
                emit(NetworkCallState.error("Не удалось получить данные: ${workoutVideoResponse.code()} - ${workoutVideoResponse.message()}"))
            }
        } catch (e: Exception) {
            emit(NetworkCallState.error("Ошибка загрузки данных: ${e.localizedMessage}"))
        }
    }
}
