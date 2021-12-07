package com.example.foodylearn.data

import com.example.foodylearn.data.network.FoodRecipesApi
import com.example.foodylearn.models.FoodRecipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

     suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipes> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipes> {
        return foodRecipesApi.searchRecipes(queries)
    }
}