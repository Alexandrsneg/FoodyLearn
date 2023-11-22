package com.example.domain.usecase

import com.example.domain.models.FoodJoke
import com.example.domain.models.NetworkResult

interface IGetJokeUseCase {
    suspend fun execute(apiKey: String): NetworkResult<String>
}