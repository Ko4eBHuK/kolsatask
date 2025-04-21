package com.okladnikov.kolsatask.section.workout.data

import com.okladnikov.kolsatask.domain.VideoWorkout
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkoutApi {
    @GET("get_video")
    suspend fun getWorkoutVideo(@Query("id") id: Int): Response<VideoWorkout>
}
