package com.okladnikov.kolsatask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://ref.test.kolsa.ru"

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType())
        .build()
}