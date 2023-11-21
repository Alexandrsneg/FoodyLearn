package com.example.domain.usecase

import com.example.domain.models.FoodRecipesRest
import com.example.domain.models.NetworkResult

interface IGetRecipesUseCase {
    suspend fun getRecipes(
        queries: Map<String, String>
    ): NetworkResult<FoodRecipesRest>
}