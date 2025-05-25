package com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state

data class WorkoutListState(
    val list: List<WorkoutCardState> = emptyList(),
    val isSearching: Boolean = false,
    val searchText: String = "",
    val searchResults: List<WorkoutCardState> = emptyList(),
)