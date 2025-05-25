package com.syhan.maximumfitness.feature_workouts.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.syhan.maximumfitness.R

enum class WorkoutType(
    @StringRes val typeName: Int,
    @ColorRes val typeColor: Int,
    @ColorRes val textColor: Int,
) {
    Exercise(R.string.type_exercise, R.color.colorExercise, R.color.onColorExercise),
    Aether(R.string.type_aether, R.color.colorAether, R.color.onColorAether),
    Complex(R.string.type_complex, R.color.colorComplex, R.color.onColorComplex)
}

