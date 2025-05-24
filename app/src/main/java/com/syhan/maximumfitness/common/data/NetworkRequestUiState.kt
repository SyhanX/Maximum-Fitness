package com.syhan.maximumfitness.common.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import okio.IOException

private const val TAG = "NetworkResponse"

sealed interface NetworkRequestUiState {
    data object Loading : NetworkRequestUiState
    data class Error(val type: ErrorType): NetworkRequestUiState
    data object Success : NetworkRequestUiState
}

sealed interface ErrorType {
    data object NoConnectionException : ErrorType
    data object UnexpectedHttpException : ErrorType
}

class EmptyHttpBodyException(msg: String) : Exception(msg)


object NetworkStateHandler {
    fun MutableStateFlow<NetworkRequestUiState>.setLoading() {
        Log.i(TAG, "Loading data")
        this.value = NetworkRequestUiState.Loading
    }

    fun MutableStateFlow<NetworkRequestUiState>.setSuccess() {
        Log.i(TAG, "Loaded successfully")
        this.value = NetworkRequestUiState.Success
    }

    fun MutableStateFlow<NetworkRequestUiState>.setError(exception: Exception) {
        Log.e(TAG, exception.message ?: "something totally unexpected occurred wow")
        this.value = if (exception is IOException) {
            NetworkRequestUiState.Error(ErrorType.NoConnectionException)
        } else NetworkRequestUiState.Error(ErrorType.UnexpectedHttpException)
    }
}