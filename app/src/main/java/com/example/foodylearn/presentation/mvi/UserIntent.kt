package com.example.foodylearn.presentation.mvi

sealed class UserIntent {
    class OnGetRecipes(val queries: Map<String, String>) : UserIntent()
    object OnApplyFilterQuery: UserIntent()
    object OnGetJoke: UserIntent()
    object OnGetFavoriteRecipes: UserIntent()
}
