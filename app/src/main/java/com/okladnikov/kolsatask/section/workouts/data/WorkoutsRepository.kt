package com.okladnikov.kolsatask.section.workouts.data

import com.okladnikov.kolsatask.utils.NetworkCallState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutsRepository @Inject constructor(
    private val api: WorkoutsApi
) {
    fun getWorkouts() = flow {
        emit(NetworkCallState.loading("Загрузка тренировок"))
        try {
            val workoutsResponse = api.getWorkouts()
            if (workoutsResponse.isSuccessful) {
                if (workoutsResponse.body() != null) {
                    workoutsResponse.body()?.let { emit(NetworkCallState.success(it)) }
                }
            } else {
                emit(NetworkCallState.error("Не удалось получить список тренировок: ${workoutsResponse.code()} - ${workoutsResponse.message()}"))
            }
        } catch (e: Exception) {
            emit(NetworkCallState.error("Ошибка загрузки тренировок: ${e.localizedMessage}"))
        }
    }
}
