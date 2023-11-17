package com.example.foodylearn.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.data.database.recipes.RecipesEntity
import com.example.domain.models.FoodJoke
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.util.Constants
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetJokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getJokeUseCase: IGetJokeUseCase,
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {


    /** ROOM */
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()
    val readJoke: LiveData<JokeEntity?> = repository.local.readJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavorites(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavorite(favoritesEntity)
        }

    fun deleteFavorite(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavorite(favoritesEntity)
        }

    fun deleteAllFavorites() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavorites()
        }

    fun insertJoke(jokeEntity: JokeEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertJoke(jokeEntity)
        }



    /** RETROFIT */
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()
    var jokeResponse: MutableLiveData<NetworkResult<FoodJoke?>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>, isSearch: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        getRecipesSafeCall(queries, isSearch)
    }

    fun getJoke() = viewModelScope.launch(Dispatchers.Main) {
        getJokeFromRest()
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>, isSearch: Boolean) {
        recipesResponse.value = NetworkResult.Loading()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (hasInternetConnection()) {
                getCall(queries, isSearch)
            } else {
                recipesResponse.value = NetworkResult.Error("No Internet Connection")
            }
        } else {
            getCall(queries, isSearch)
        }

    }

    private suspend fun getCall(queries: Map<String, String>, isSearch: Boolean){
        try {
            var response = repository.remote.getRecipes(queries)
            if (isSearch)
                response = repository.remote.searchRecipes(queries)
            recipesResponse.value = handleFoodRecipesResponse(response)

            recipesResponse.value?.data?.let {
                insertRecipes(RecipesEntity(it))
            }
        } catch (e: Exception) {
            recipesResponse.value = NetworkResult.Error(e.message)
        }
    }

    private suspend fun getJokeFromRest() {
        jokeResponse.value = NetworkResult.Loading()
        val getJokeResult = getJokeUseCase.execute(hasInternetConnection(), Constants.API_KEY)
        jokeResponse.value = getJokeResult
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipes>): NetworkResult<FoodRecipes> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.body()?.recipes.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }


    private fun hasInternetConnection(): Boolean {
        // cant define for pre 23 api
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true

        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}