package com.example.foodylearn.presentation.viewmodels

import androidx.lifecycle.*
import com.example.foodylearn.data.Repository
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.data.database.recipes.RecipesEntity
import com.example.domain.models.FoodRecipesRest
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.util.Constants
import com.example.domain.models.NetworkResult
import com.example.domain.usecase.IGetJokeUseCase
import com.example.domain.usecase.IGetRecipesUseCase
import com.example.foodylearn.data.toFoodRecipes
import com.example.foodylearn.presentation.mvi.JokeFragmentState
import com.example.foodylearn.presentation.mvi.MainScreenUserIntent
import com.example.foodylearn.presentation.mvi.RecipesFragmentState
import com.example.foodylearn.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getJokeUseCase: IGetJokeUseCase,
    private val getRecipesUseCase: IGetRecipesUseCase,
    private val repository: Repository
): ViewModel() {

    //RECIPES
    private val _recipesFragmentState = MutableStateFlow(RecipesFragmentState(isLoading = false))
    val recipesFragmentState: StateFlow<RecipesFragmentState> = _recipesFragmentState

    private val _jokeFragmentState = MutableStateFlow(JokeFragmentState(isLoading = false, ""))
    val jokeFragmentState: StateFlow<JokeFragmentState> = _jokeFragmentState

    fun onMainScreenIntent(intent: MainScreenUserIntent) {
        when (intent) {
            is MainScreenUserIntent.OnGetRecipes -> onGetRecipes(intent.queries)
            MainScreenUserIntent.OnGetJoke -> onGetJoke()
            MainScreenUserIntent.OnApplyFilterQuery -> TODO()
        }
    }

    /************** RECIPES_LIST ***************************/
    private fun onGetRecipes(queries: Map<String, String>) {
        viewModelScope.launch(Dispatchers.Main) {
            _recipesFragmentState.update { it.copy(isLoading = true) }
            val result = getRecipesUseCase.getRecipes(queries)
            _recipesFragmentState.update { it.copy(isLoading = false) }
            when (result) {
                is NetworkResult.Success -> onGetRecipesSuccess(result.data)
                is NetworkResult.Error -> onGetRecipesError(result.message)
            }
        }
    }

    private fun onGetRecipesSuccess(foodRecipesRest: FoodRecipesRest) {
        val foodRecipes = foodRecipesRest.toFoodRecipes()
        _recipesFragmentState.update {
            it.copy(recipes = foodRecipes, error = null)
        }
        insertRecipes(RecipesEntity(foodRecipes))
    }

    private fun onGetRecipesError(message: String) {
        _recipesFragmentState.update {
            it.copy(recipes = FoodRecipes(emptyList()), error = UiText.DynamicString(message))
        }
    }


    /************** JOKE ***************************/
    private fun onGetJoke() {
        viewModelScope.launch (Dispatchers.Main) {
            _jokeFragmentState.update { it.copy(isLoading = true)}
            val getJokeResult = getJokeUseCase.execute(Constants.API_KEY)
            _jokeFragmentState.update { it.copy(isLoading = false)}
            when (getJokeResult) {
                is NetworkResult.Success -> onGetJokeSuccess(getJokeResult.data)
                is NetworkResult.Error -> onGetJokeError(getJokeResult.message)
            }
        }
    }
    private fun onGetJokeSuccess(joke: String) {
        _jokeFragmentState.update { it.copy(joke = joke) }
    }

    private fun onGetJokeError(message: String) {
        _jokeFragmentState.update { it.copy(error = UiText.DynamicString(message)) }
    }


    /** ROOM */
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavorites(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavorite(favoritesEntity)
        }

    fun deleteFavoriteById(recipeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavorite(recipeId)
        }

    fun deleteAllFavorites() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavorites()
        }

    fun insertJoke(jokeEntity: JokeEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertJoke(jokeEntity)
        }

}