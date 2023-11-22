package com.example.foodylearn.presentation.mvi

sealed class MainScreenUserIntent {
    class OnGetRecipes(val queries: Map<String, String>) : MainScreenUserIntent()
    object OnApplyFilterQuery: MainScreenUserIntent()
    object OnGetJoke: MainScreenUserIntent()
}
