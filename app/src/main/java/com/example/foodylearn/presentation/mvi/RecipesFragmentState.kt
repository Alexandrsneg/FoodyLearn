package com.example.foodylearn.presentation.mvi

import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.util.UiText

data class RecipesFragmentState(
    val isLoading: Boolean,
    val recipes: FoodRecipes = FoodRecipes(emptyList()),
    val error: UiText? = null
)
