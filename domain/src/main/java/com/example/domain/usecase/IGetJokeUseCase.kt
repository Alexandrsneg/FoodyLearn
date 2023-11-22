package com.example.domain.usecase

import com.example.domain.models.Result

interface IGetJokeUseCase {
    suspend fun execute(apiKey: String): Result<String>
}