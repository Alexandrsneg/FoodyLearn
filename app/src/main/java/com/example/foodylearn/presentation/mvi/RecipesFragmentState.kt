package com.example.foodylearn.presentation.mvi

import com.example.domain.models.FoodRecipesClean
import com.example.foodylearn.util.UiText

data class RecipesFragmentState(
    val isLoading: Boolean,
    val recipes: FoodRecipesClean = FoodRecipesClean(emptyList()),
    val error: UiText? = null
)
