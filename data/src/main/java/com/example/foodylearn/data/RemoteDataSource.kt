package com.example.foodylearn.data

import com.example.foodylearn.data.models.FoodJoke
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.data.network.FoodRecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

     suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipes> =
         withContext(Dispatchers.IO) {
            foodRecipesApi.getRecipes(queries)
         }

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipes> =
         withContext(Dispatchers.IO) {
            foodRecipesApi.searchRecipes(queries)
        }

    suspend fun getJoke(apiKey: String): Response<FoodJoke> =
        withContext(Dispatchers.IO) {
            foodRecipesApi.getJoke(apiKey)
        }
}