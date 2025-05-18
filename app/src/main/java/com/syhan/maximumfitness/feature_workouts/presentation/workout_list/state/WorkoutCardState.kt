package com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state

data class WorkoutCardState(
    val id: Int = -1,
    val title: String = "",
    val description: String? = null,
    val type: Int = 0,
    val duration: String = "Unknown",
    val onClick: (id: Int) -> Unit = {}
)