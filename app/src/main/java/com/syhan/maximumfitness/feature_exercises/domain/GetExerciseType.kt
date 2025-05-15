package com.syhan.maximumfitness.feature_exercises.domain

fun getExerciseType(type: Int) : String {
    return when (type) {
        1 -> "Тренировка"
        2 -> "Эфир"
        3 -> "Комлпекс"
        else -> ""
    }
}
