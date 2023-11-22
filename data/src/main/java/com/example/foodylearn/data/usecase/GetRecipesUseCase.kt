package com.example.foodylearn.data.usecase

import com.example.domain.models.FoodRecipesRest
import com.example.domain.models.Result
import com.example.domain.usecase.IGetRecipesUseCase
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.extensions_data.isNotNullAndNotEmpty
import com.example.foodylearn.data.toFoodRecipesRest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher
) : IGetRecipesUseCase {
    override suspend fun getRecipes(queries: Map<String, String>): Result<FoodRecipesRest> {
        return try {
            withContext(dispatcher) {
                val cachedRecipes = repository.local.readRecipes().toFoodRecipesRest()
                val response = repository.remote.getRecipes(queries)
                when {
                    response.body()?.results != null ->
                        Result.Success(response.body()!!)

                    response.errorBody() != null -> {
                        cachedRecipes?.results?.isNotNullAndNotEmpty()?.let {
                            return@withContext Result.Success(cachedRecipes)
                        } ?: run {
                            return@withContext Result.Error(response.message())
                        }
                    }

                    else -> Result.Error("Oops, try later")
                }
            }
        } catch (exception: Exception) {
            Result.Error(exception.message ?: "Oops, try later")
        }
    }
}