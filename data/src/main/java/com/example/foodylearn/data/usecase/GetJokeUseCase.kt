package com.example.foodylearn.data.usecase

import com.example.domain.models.Result
import com.example.domain.usecase.IGetJokeUseCase
import com.example.foodylearn.data.Constants
import com.example.foodylearn.data.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class GetJokeUseCase @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher
) : IGetJokeUseCase {
    override suspend fun execute(apiKey: String): Result<String> {
        return try  {
            withContext(dispatcher) {
                val response = repository.remote.getJoke(apiKey)
                when {
                    !response.body()?.text.isNullOrBlank() ->
                        return@withContext Result.Success(response.body()!!.text!!)
                    response.errorBody() != null -> {
                        repository.local.readJoke()?.let {
                            return@withContext Result.Success(it.joke.text ?: "Joke is empty...")
                        } ?: run {
                            return@withContext Result.Error(response.message())
                        }
                    }
                    else -> return@withContext Result.Error("Oops, try later")
                }
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: Constants.COMMON_ERROR_MSG)
        }
    }
}