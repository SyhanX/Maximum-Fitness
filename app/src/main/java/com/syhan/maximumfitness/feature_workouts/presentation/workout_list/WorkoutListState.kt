package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import com.syhan.maximumfitness.feature_workouts.presentation.WorkoutState

data class WorkoutListState(
    val list: List<WorkoutState> = emptyList(),
    val searchResults: List<WorkoutState> = emptyList(),
    val sortedList: List<WorkoutState> = emptyList(),
    val isSearching: Boolean = false,
    val searchText: String? = null,
    val sortBy: SortByType = SortByType.SortByDefault
)

sealed class SortByType(val index: Int) {
    data object SortByDefault : SortByType(0)
    data object SortByExercise : SortByType(1)
    data object SortByAether : SortByType(2)
    data object SortByComplex : SortByType(3)
}