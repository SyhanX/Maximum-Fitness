package com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutCardState(
    val id: Int = -1,
    val title: String = "",
    val description: String? = null,
    val type: Int = 1,
    val duration: Int = 0,
)