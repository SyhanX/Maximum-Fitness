package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syhan.maximumfitness.common.data.EmptyHttpBodyException
import com.syhan.maximumfitness.common.data.ErrorType
import com.syhan.maximumfitness.common.data.NetworkRequestUiState
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setError
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setLoading
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setSuccess
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.WorkoutCardState
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.WorkoutListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

private const val TAG = "WorkoutListViewModel"

class WorkoutListViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(WorkoutListState())
    val listState = _listState.asStateFlow()

    private val _networkUiState = MutableStateFlow<NetworkRequestUiState>(NetworkRequestUiState.Loading)
    val networkUiState = _networkUiState.asStateFlow()

    init {
        loadWorkoutList()
    }

    fun retryLoadingWorkoutList() {
        loadWorkoutList()
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
                        WorkoutCardState(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            type = it.type,
                            duration = it.duration ?: -1,
                        )
                    }
                )
                _networkUiState.setSuccess()
            } catch (e: IOException) {
                _networkUiState.setError(ErrorType.NoConnectionException, e.message)
            } catch (e: Exception) {
                _networkUiState.setError(ErrorType.UnexpectedHttpException, e.message)
            }
        }
    }
}