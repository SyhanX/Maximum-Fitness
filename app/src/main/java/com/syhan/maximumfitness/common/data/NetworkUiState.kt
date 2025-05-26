package com.syhan.maximumfitness.common.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import okio.IOException

private const val TAG = "NetworkUiState"

sealed interface NetworkUiState {
    data object Loading : NetworkUiState
    data class Error(val type: ErrorType): NetworkUiState
    data object Success : NetworkUiState
}

sealed interface ErrorType {
    data object ConnectionError : ErrorType
    data object HttpError : ErrorType
}

class EmptyHttpBodyException(msg: String) : Exception(msg)


object NetworkStateHandler {
    fun MutableStateFlow<NetworkUiState>.setLoading() {
        Log.i(TAG, "Loading data")
        this.value = NetworkUiState.Loading
    }

    fun MutableStateFlow<NetworkUiState>.setSuccess() {
        Log.i(TAG, "Loaded successfully")
        this.value = NetworkUiState.Success
    }

    fun MutableStateFlow<NetworkUiState>.setError(exception: Exception) {
        Log.e(TAG, exception.message ?: "something weird happened")
        this.value = if (exception is IOException) {
            NetworkUiState.Error(ErrorType.ConnectionError)
        } else NetworkUiState.Error(ErrorType.HttpError)
    }
}