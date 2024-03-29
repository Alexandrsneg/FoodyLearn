package com.example.foodylearn.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Recipe(
    val aggregateLikes: Int?,
    val cheap: Boolean?,
    val dairyFree: Boolean?,
    val extendedIngredients: List<ExtendedIngredient>?,
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
): Parcelable{}