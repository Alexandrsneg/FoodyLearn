package com.example.foodylearn.data.usecase

import com.example.domain.models.FoodRecipesRest
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetRecipesUseCase
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.extensions_data.isNotNullAndNotEmpty
import com.example.foodylearn.data.toFoodRecipesRest
import java.lang.Exception
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: Repository
) : IGetRecipesUseCase {
    override suspend fun getRecipes(queries: Map<String, String>): NetworkResult<FoodRecipesRest> {
        return try {
            val cachedRecipes = repository.local.readRecipes().toFoodRecipesRest()
            val response = repository.remote.getRecipes(queries)
            when {
                response.body()?.results != null -> NetworkResult.Success(response.body()!!)
                response.errorBody() != null -> {
                    cachedRecipes?.results?.isNotNullAndNotEmpty()?.let {
                        NetworkResult.Success(cachedRecipes)
                    } ?: run {
                        NetworkResult.Error(response.message())
                    }
                }
                else -> NetworkResult.Error("Oops, try later")
            }
        } catch (exception: Exception) {
            NetworkResult.Error(exception.message ?: "Oops, try later")
        }
    }
}