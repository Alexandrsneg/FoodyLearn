package com.example.foodylearn.data.usecase

import com.example.domain.models.FoodJoke
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetJokeUseCase
import com.example.foodylearn.data.Repository
import java.lang.Exception
import javax.inject.Inject


class GetJokeUseCase @Inject constructor(
    private val repository: Repository
) : IGetJokeUseCase {
    override suspend fun execute(hasInternetConnection: Boolean?, apiKey: String): NetworkResult<FoodJoke?> {
        return try {
            val response = repository.remote.getJoke(apiKey)
            when {
                response.body() != null -> NetworkResult.Success(response.body())
                response.errorBody() != null -> NetworkResult.Error(response.code().toString())
                else -> NetworkResult.Error("Oops, try later")
            }
        } catch (exception: Exception) {
            NetworkResult.Error("No Internet Connection")
        }
    }
}