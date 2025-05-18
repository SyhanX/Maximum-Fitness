package com.syhan.maximumfitness.feature_workouts.data

import androidx.annotation.StringRes
import com.syhan.maximumfitness.R

enum class WorkoutType(@StringRes val typeName: Int) {
    Exercise(R.string.exercise_type_1),
    Aether(R.string.exercise_type_2),
    Complex(R.string.exercise_type_3)
}

