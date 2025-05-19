package com.syhan.maximumfitness.feature_workouts.presentation.workout_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository

private const val TAG = "WorkoutDetailsViewModel"

class WorkoutDetailsViewModel(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var args: Int? = null

    init {
        args = savedStateHandle.get<Int>("workoutId")
        Log.d(TAG, "init: $args")
    }
}
