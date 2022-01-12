package com.example.foodylearn.data.network

import com.example.foodylearn.models.FoodJoke
import com.example.foodylearn.models.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ) : Response<FoodRecipes>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ) : Response<FoodRecipes>

    @GET("food/jokes/random")
    suspend fun getJoke(
        @Query("apiKey") apiKey: String
    ) : Response<FoodJoke>

}