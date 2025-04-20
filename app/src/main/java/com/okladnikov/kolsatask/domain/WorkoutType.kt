package com.okladnikov.kolsatask.domain

enum class WorkoutType(
    val value: Int,
    val title: String
) {
    TRAINING(1, "Тренировка"),
    STREAM(2, "Эфир"),
    COMPLEX(3, "Комплекс");

    companion object {
        infix fun from(value: Int): WorkoutType = WorkoutType.entries.firstOrNull { it.value == value } ?: TRAINING
    }
}
