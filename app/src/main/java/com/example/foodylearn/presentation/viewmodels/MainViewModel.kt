package com.example.foodylearn.presentation.viewmodels

import androidx.lifecycle.*
import com.example.domain.models.FoodRecipesClean
import com.example.domain.models.Result
import com.example.domain.usecase.GetFavoriteRecipesUseCase
import com.example.domain.usecase.GetJokeUseCaseNew
import com.example.domain.usecase.GetRecipesUseCase
import com.example.foodylearn.presentation.mvi.JokeFragmentState
import com.example.foodylearn.presentation.mvi.UserIntent
import com.example.foodylearn.presentation.mvi.RecipesFragmentState
import com.example.foodylearn.util.UiText
import com.example.foodylearn.util.exceptionHandler
import com.example.foodylearn.util.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getJokeUseCase: GetJokeUseCaseNew,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
) : ViewModel() {

    //RECIPES_STATE
    private val _recipesFragmentState = MutableStateFlow(RecipesFragmentState(isLoading = false))
    val recipesFragmentState: StateFlow<RecipesFragmentState> = _recipesFragmentState

    //RECIPES_STATE
    private val _favoriteRecipesFragmentState = MutableStateFlow(RecipesFragmentState(isLoading = false))
    val favoriteRecipesFragmentState: StateFlow<RecipesFragmentState> = _favoriteRecipesFragmentState

    //JOKE_STATE
    private val _jokeFragmentState = MutableStateFlow(JokeFragmentState(isLoading = false, ""))
    val jokeFragmentState: StateFlow<JokeFragmentState> = _jokeFragmentState

    //TODO favoritesFragmentState

    fun onMainScreenIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.OnGetRecipes -> onGetRecipes(intent.queries)
            UserIntent.OnGetFavoriteRecipes -> onGetFavoriteRecipes()
            UserIntent.OnGetJoke -> onGetJoke()
            UserIntent.OnApplyFilterQuery -> {
                //TODO
            }
        }
    }

    /************** RECIPES_LIST ***************************/
    private fun onGetRecipes(queries: Map<String, String>) {
        mainScope(exceptionHandler = exceptionHandler { onGetRecipesError(it) }) {
            _recipesFragmentState.update { it.copy(isLoading = true) }
            val result = getRecipesUseCase.invoke(queries)
            _recipesFragmentState.update { it.copy(isLoading = false) }
            when (result) {
                is Result.Success -> onGetRecipesSuccess(result.data)
                is Result.Error -> onGetRecipesError(result.message)
            }
        }
    }


    private fun onGetRecipesSuccess(foodRecipesClean: FoodRecipesClean) {
        _recipesFragmentState.update {
            it.copy(recipes = foodRecipesClean, error = null)
        }
    }

    private fun onGetRecipesError(message: String) {
        _recipesFragmentState.update {
            it.copy(recipes = FoodRecipesClean(emptyList()), error = UiText.DynamicString(message))
        }
    }

    /************** RECIPES_LIST ***************************/
    private fun onGetFavoriteRecipes() {
        mainScope(exceptionHandler = exceptionHandler { onGetFavoriteRecipesError(it) }) {
            _favoriteRecipesFragmentState.update { it.copy(isLoading = true) }
            val result = getFavoriteRecipesUseCase.invoke()
            _favoriteRecipesFragmentState.update { it.copy(isLoading = false) }
            when (result) {
                is Result.Success -> onGetRecipesSuccess(result.data)
                is Result.Error -> onGetRecipesError(result.message)
            }
        }
    }


    private fun onGetFavoriteRecipesSuccess(foodRecipesClean: FoodRecipesClean) {
        _favoriteRecipesFragmentState.update {
            it.copy(recipes = foodRecipesClean, error = null)
        }
    }

    private fun onGetFavoriteRecipesError(message: String) {
        _favoriteRecipesFragmentState.update {
            it.copy(recipes = FoodRecipesClean(emptyList()), error = UiText.DynamicString(message))
        }
    }


    /************** JOKE ***************************/
    private fun onGetJoke() {
        mainScope(exceptionHandler = exceptionHandler {
            onGetJokeError(it)
        }) {
            _jokeFragmentState.update { it.copy(isLoading = true) }
            val getJokeResult = getJokeUseCase.invoke()
            _jokeFragmentState.update { it.copy(isLoading = false) }
            when (getJokeResult) {
                is Result.Success -> onGetJokeSuccess(getJokeResult.data)
                is Result.Error ->onGetJokeError(getJokeResult.message)
            }
        }
    }

    private fun onGetJokeSuccess(joke: String) {
        println("IVANOV, onGetJokeSuccess")
        _jokeFragmentState.update { it.copy(joke = joke, error = null) }
    }

    private fun onGetJokeError(message: String) {
        println("IVANOV, onGetJokeError: $message")
        _jokeFragmentState.update { it.copy(error = UiText.DynamicString(message)) }
    }


    /** ROOM */
    //todo Local read, insert and delete functions and useCases

}