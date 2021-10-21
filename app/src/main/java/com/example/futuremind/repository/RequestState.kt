package com.example.futuremind.repository

import com.example.futuremind.view.error.ErrorState

sealed class RequestState<out T> {
    data class Success<out T>(val data: T) : RequestState<T>()
    data class Loading<out T>(val data: T) : RequestState<T>()
    data class Error(val errorState: ErrorState) : RequestState<Nothing>() {
        fun isNetworkError() = errorState == ErrorState.NETWORK_ERROR
        fun isDataError() = errorState == ErrorState.DATA_ERROR
    }
}