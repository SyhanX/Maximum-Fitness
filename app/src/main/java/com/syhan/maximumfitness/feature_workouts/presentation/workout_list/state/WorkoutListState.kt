package com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state

data class WorkoutListState(
    val list: List<WorkoutCardState> = emptyList(),
    val searchResults: List<WorkoutCardState> = emptyList(),
    val sortedList: List<WorkoutCardState> = emptyList(),
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