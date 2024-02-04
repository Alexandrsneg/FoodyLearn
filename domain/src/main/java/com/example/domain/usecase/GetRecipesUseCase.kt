package com.example.domain.usecase

import com.example.domain.UseCaseConstants
import com.example.domain.models.FoodRecipesClean
import com.example.domain.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: IRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(queries: Map<String, String>): Result<FoodRecipesClean> =
        withContext(dispatcher) {
            val result = repository.getRecipes(queries).data
                ?: return@withContext Result.Error(UseCaseConstants.DATA_IS_NULL_OR_EMPTY)
            Result.Success(result)
        }
}