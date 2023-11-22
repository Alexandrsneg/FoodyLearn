package com.example.foodylearn.data.usecase

import com.example.domain.models.FoodRecipesRest
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetRecipesUseCase
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.extensions_data.isNotNullAndNotEmpty
import com.example.foodylearn.data.toFoodRecipesRest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher
) : IGetRecipesUseCase {
    override suspend fun getRecipes(queries: Map<String, String>): NetworkResult<FoodRecipesRest> {
        return try {
            withContext(dispatcher) {
                val cachedRecipes = repository.local.readRecipes().toFoodRecipesRest()
                val response = repository.remote.getRecipes(queries)
                when {
                    response.body()?.results != null ->
                        NetworkResult.Success(response.body()!!)

                    response.errorBody() != null -> {
                        cachedRecipes?.results?.isNotNullAndNotEmpty()?.let {
                            return@withContext NetworkResult.Success(cachedRecipes)
                        } ?: run {
                            return@withContext NetworkResult.Error(response.message())
                        }
                    }

                    else -> NetworkResult.Error("Oops, try later")
                }
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message ?: "Oops, try later")
        }
    }
}