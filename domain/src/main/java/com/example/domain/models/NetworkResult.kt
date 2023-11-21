package com.example.domain.models

sealed class NetworkResult<T>() {
    class Success<T>(val data: T): NetworkResult<T>()
    class Error<T>(val message: String): NetworkResult<T>()
}
