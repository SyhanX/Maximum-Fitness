package com.syhan.maximumfitness.feature_workouts.presentation.workout_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syhan.maximumfitness.common.data.EmptyHttpBodyException
import com.syhan.maximumfitness.common.data.NetworkUiState
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setError
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setLoading
import com.syhan.maximumfitness.common.data.NetworkStateHandler.setSuccess
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.WorkoutCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private const val TAG = "WorkoutDetailsViewModel"

class WorkoutDetailsViewModel(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _detailsState = MutableStateFlow(WorkoutCardState())
    val detailsState = _detailsState.asStateFlow()

    private val _videoState = MutableStateFlow(WorkoutVideoState())
    val videoState = _videoState.asStateFlow()

    private val _networkState =
        MutableStateFlow<NetworkUiState>(NetworkUiState.Loading)
    val networkState = _networkState.asStateFlow()

    private var args: String? = savedStateHandle.get<String>("workoutState")

    init {
        getWorkoutDetails()
        loadWorkoutVideo()
    }

    private fun getWorkoutDetails() {
        val decoded = args?.let {
            Json.decodeFromString<WorkoutCardState>(it)
        } ?: WorkoutCardState()
        _detailsState.value = detailsState.value.copy(
            id = decoded.id,
            title = decoded.title,
            description = decoded.description,
            type = decoded.type,
            duration = decoded.duration
        )
    }

    private fun loadWorkoutVideo() {
        viewModelScope.launch {
            _networkState.setLoading()
            try {
                if (_detailsState.value.id != -1) {
                    val response = repository.getVideo(_detailsState.value.id)
                    val body = response.body() ?: throw EmptyHttpBodyException("empty body")
                    _videoState.value = videoState.value.copy(
                        id = body.id,
                        duration = body.duration.toString(),
                        link = body.link
                    )
                    _networkState.setSuccess()
                } else throw Exception("Invalid id ${_videoState.value.id}")
            } catch (e: Exception) {
                _networkState.setError(exception = e)
            }
        }
    }
}
