package com.syhan.maximumfitness.feature_workouts.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.syhan.maximumfitness.R

enum class WorkoutType(
    @StringRes val typeName: Int,
    @ColorRes val typeColor: Int,
    @ColorRes val textColor: Int,
) {
    Exercise(R.string.exercise_type_1, R.color.colorExercise, R.color.onColorExercise),
    Aether(R.string.exercise_type_2, R.color.colorAether, R.color.onColorAether),
    Complex(R.string.exercise_type_3, R.color.colorComplex, R.color.onColorComplex)
}

