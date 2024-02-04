package com.example.foodylearn.data.data_sources

import com.example.domain.models.FoodJokeClean
import com.example.domain.models.FoodRecipesClean
import com.example.foodylearn.data.network.FoodRecipesApi
import com.example.foodylearn.data.toFoodRecipesClean
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): FoodRecipesClean =
        foodRecipesApi.getRecipes(queries).toFoodRecipesClean()


//    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipes> =
//         withContext(Dispatchers.IO) {
//            foodRecipesApi.searchRecipes(queries)
//        }

    suspend fun getJoke(): FoodJokeClean = foodRecipesApi.getJoke()
    fun getJokeRx(apiKey: String): Observable<List<Int>> = Observable.just(listOf(1,2,3))

    fun getJokeSRx(id: Int): Observable<FoodJokeClean> = Observable.create() {
        it.onNext(FoodJokeClean("mock joke observable, id - $id "))
        it.onComplete()
    }
}