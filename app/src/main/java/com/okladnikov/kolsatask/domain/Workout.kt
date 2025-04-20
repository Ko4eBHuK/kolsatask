package com.okladnikov.kolsatask.domain

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    val id: Int,
    val title: String,
    val description: String?,
    val type: Int,
    val duration: String
)
