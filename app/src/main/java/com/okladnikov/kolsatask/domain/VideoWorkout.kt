package com.okladnikov.kolsatask.domain

import kotlinx.serialization.Serializable

@Serializable
data class VideoWorkout(
    val id: Int,
    val duration: String,
    val link: String
)
