package com.example.domain.models

data class RecipeClean(
    val aggregateLikes: Int?,
    val cheap: Boolean?,
    val dairyFree: Boolean?,
    val extendedIngredientCleans: List<ExtendedIngredientClean>?,
    val glutenFree: Boolean?,
    val id: Int?,
    val image: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceName: String?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val title: String?,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val weightWatcherSmartPoints: Int?
)
