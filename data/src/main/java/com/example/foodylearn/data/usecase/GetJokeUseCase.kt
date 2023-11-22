package com.example.foodylearn.data.usecase

import com.example.domain.models.FoodJoke
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetJokeUseCase
import com.example.foodylearn.data.Constants
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.toFoodRecipesRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class GetJokeUseCase @Inject constructor(
    private val repository: Repository
) : IGetJokeUseCase {
    override suspend fun execute(apiKey: String): NetworkResult<String> {
        return try  {
            withContext(Dispatchers.IO) {
                val cachedJoke = repository.local.readJoke()
                val response = repository.remote.getJoke(apiKey)
                when {
                    !response.body()?.text.isNullOrBlank() ->
                         NetworkResult.Success(response.body()!!.text!!)
                    response.errorBody() != null -> {
                        cachedJoke?.let {
                            return@withContext NetworkResult.Success(it.joke.text ?: "Joke is empty...")
                        } ?: run {
                            return@withContext NetworkResult.Error(response.message())
                        }
                    }
                    else -> NetworkResult.Error("Oops, try later")
                }
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message ?: Constants.COMMON_ERROR_MSG)
        }
    }
}