package com.example.domain.usecase

import com.example.domain.UseCaseConstants
import com.example.domain.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import javax.inject.Inject

class GetJokeUseCaseNew @Inject constructor(
    private val repository: IRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<String> = withContext(dispatcher) {
        val result = repository.getJoke().data?.text
                ?: return@withContext Result.Error(UseCaseConstants.DATA_IS_NULL_OR_EMPTY)
        Result.Success(result)
    }
}