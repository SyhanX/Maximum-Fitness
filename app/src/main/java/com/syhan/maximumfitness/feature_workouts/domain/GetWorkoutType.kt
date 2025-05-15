package com.syhan.maximumfitness.feature_workouts.domain

fun getExerciseType(type: Int) : String {
    return when (type) {
        1 -> "Тренировка"
        2 -> "Эфир"
        3 -> "Комлпекс"
        else -> ""
    }
}
