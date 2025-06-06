package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syhan.maximumfitness.common.data.EmptyHttpBodyException
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setError
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setLoading
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setSuccess
import com.syhan.maximumfitness.common.data.NetworkUiState
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository
import com.syhan.maximumfitness.feature_workouts.presentation.WorkoutState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "WorkoutListViewModel"

class WorkoutListViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(WorkoutListState())
    val listState = _listState.asStateFlow()

    private val _networkUiState =
        MutableStateFlow<NetworkUiState>(NetworkUiState.Loading)
    val networkUiState = _networkUiState.asStateFlow()

    init {
        loadWorkoutList()
    }

    fun retryLoadingWorkoutList() {
        loadWorkoutList()
    }

    fun changeSortingType(type: SortByType) {
        _listState.value = listState.value.copy(
            sortBy = type
        )
        sortExercisesByType()
    }

    private fun sortExercisesByType() {
        val filteredList = listState.value.list.filter {
            it.type == listState.value.sortBy.index
        }
        _listState.value = listState.value.copy(
            sortedList = filteredList
        )
    }

    private fun loadWorkoutList() {
        viewModelScope.launch(Dispatchers.IO) {
            _networkUiState.setLoading()
            try {
                val response = repository.getWorkouts()
                val body = response.body()
                    ?: throw EmptyHttpBodyException("this thing is emptier than my soul")

                _listState.value = listState.value.copy(
                    list = body.map {
                        WorkoutState(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            type = it.type,
                            duration = it.duration ?: -1,
                        )
                    }
                )
                _networkUiState.setSuccess()
            } catch (e: Exception) {
                _networkUiState.setError(e)
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _listState.value = listState.value.copy(
            searchText = text.ifBlank { null }
        )
    }

    fun doSearch() {
        val searchResults: List<WorkoutState> = _listState.value.list.filter {
            it.doesMatchSearchQuery(listState.value.searchText)
        }
        _listState.value = listState.value.copy(
            searchResults = searchResults,
            isSearching = searchResults.isNotEmpty()
        )
    }
}