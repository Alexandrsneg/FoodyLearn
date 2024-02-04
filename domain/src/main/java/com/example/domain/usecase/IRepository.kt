package com.example.domain.usecase

import com.example.domain.models.DataSourceResponseWrapper
import com.example.domain.models.FoodJokeClean
import com.example.domain.models.FoodRecipesClean

interface IRepository {
    suspend fun getRecipes(queries: Map<String, String>): DataSourceResponseWrapper<FoodRecipesClean>
    suspend fun getJoke(): DataSourceResponseWrapper<FoodJokeClean>
    suspend fun getFavoriteRecipes(): DataSourceResponseWrapper<FoodRecipesClean>
}