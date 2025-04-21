package com.okladnikov.kolsatask.section.workouts.data

import com.okladnikov.kolsatask.domain.Workout
import retrofit2.Response
import retrofit2.http.GET

interface WorkoutsApi {
    @GET("get_workouts")
    suspend fun getWorkouts(): Response<List<Workout>>
}
