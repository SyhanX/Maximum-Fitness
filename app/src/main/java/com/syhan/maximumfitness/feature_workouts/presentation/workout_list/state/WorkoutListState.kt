package com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state

data class WorkoutListState(
    val list: List<WorkoutCardState> = emptyList()
)