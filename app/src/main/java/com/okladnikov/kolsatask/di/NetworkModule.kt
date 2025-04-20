package com.okladnikov.kolsatask.di

import com.okladnikov.kolsatask.section.workout.data.WorkoutApi
import com.okladnikov.kolsatask.section.workouts.data.WorkoutsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://ref.test.kolsa.ru"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        )
        .build()

    @Provides
    @Singleton
    fun provideWorkoutApi(retrofit: Retrofit): WorkoutApi = retrofit.create(WorkoutApi::class.java)

    @Provides
    @Singleton
    fun provideWorkoutsApi(retrofit: Retrofit): WorkoutsApi = retrofit.create(WorkoutsApi::class.java)
}
