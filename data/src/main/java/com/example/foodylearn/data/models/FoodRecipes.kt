package com.example.foodylearn.data.models


import com.google.gson.annotations.SerializedName

data class FoodRecipes(
    @SerializedName("results")
    val recipes: List<Recipe>
)