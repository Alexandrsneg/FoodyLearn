package com.example.foodylearn.data.network

import com.example.domain.models.FoodJokeClean
import com.example.domain.models.FoodRecipesClean
import com.example.foodylearn.data.models.FoodRecipes
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ) : FoodRecipes

//
//    @GET("/recipes/complexSearch")
//    suspend fun searchRecipes(
//        @QueryMap searchQuery: Map<String, String>
//    ) : Response<FoodRecipes>

    @GET("food/jokes/random")
    suspend fun getJoke() : FoodJokeClean

    @GET("food/jokes/random")
    fun getJokeRx() : Single<FoodJokeClean>

}